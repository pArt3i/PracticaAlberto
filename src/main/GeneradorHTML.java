package main;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class GeneradorHTML {

    public String generar(List<noticia> noticias) {

        LocalDate hoy = LocalDate.now();
        String nombre = "resumen-" + hoy + ".html";
        String fichero = "reports" + File.separator + nombre;

        new File("reports").mkdirs();

        try (FileWriter fw = new FileWriter(fichero)) {

            fw.write("<html><head><meta charset='UTF-8'><title>Resumen Diario</title></head><body>");
            fw.write("<h1>Resumen de noticias del " + hoy + "</h1><hr>");

            for (noticia n : noticias) {

                LocalDate fechaNoticia =
                        new java.util.Date(n.getFecha()).toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();

                if (fechaNoticia.equals(hoy)) {

                    fw.write("<h2>" + n.getTitulo() + "</h2>");
                    fw.write("<p><a href='" + n.getLink() + "'>Leer noticia</a></p>");
                    fw.write("<p><b>Fuente:</b> " + n.getFuente() + "</p><br><br>");
                }
            }

            fw.write("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fichero;
    }
}
