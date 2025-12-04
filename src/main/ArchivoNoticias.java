package main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoNoticias {

    // OJO: data dentro de src
    private static final String FILE = "src/data/noticias.dat";

    // Tamaños en bytes (cada char = 2 bytes)
    private static final int TAM_TITULO = 200;  // 100 chars
    private static final int TAM_LINK = 300;    // 150 chars
    private static final int TAM_FUENTE = 150;  // 75 chars
    private static final int TAM_FECHA = 8;     // long

    private static final int TAM_REGISTRO =
            TAM_TITULO + TAM_LINK + TAM_FUENTE + TAM_FECHA;

    public ArchivoNoticias() {
        // Crear carpeta src/data si no existe
        File dirData = new File("src/data");
        if (!dirData.exists()) {
            dirData.mkdirs();
        }
    }

    private void escribirStringFijo(RandomAccessFile raf, String texto, int tamBytes) throws IOException {
        if (texto == null) texto = "";
        int tamChars = tamBytes / 2;
        StringBuilder sb = new StringBuilder(texto);

        if (sb.length() > tamChars) {
            sb.setLength(tamChars);
        } else {
            while (sb.length() < tamChars) {
                sb.append(' ');
            }
        }
        raf.writeChars(sb.toString());
    }

    private String leerStringFijo(RandomAccessFile raf, int tamBytes) throws IOException {
        int tamChars = tamBytes / 2;
        char[] buffer = new char[tamChars];
        for (int i = 0; i < tamChars; i++) {
            buffer[i] = raf.readChar();
        }
        return new String(buffer).trim();
    }

    public void guardarNuevas(List<noticia> lista) {
        List<noticia> guardadas = leerTodas();
        int contadorNuevas = 0;

        try (RandomAccessFile raf = new RandomAccessFile(FILE, "rw")) {

            for (noticia n : lista) {
                boolean yaExiste = guardadas.stream()
                        .anyMatch(x -> x.getTitulo().equalsIgnoreCase(n.getTitulo()));

                if (yaExiste) continue;

                raf.seek(raf.length());

                escribirStringFijo(raf, n.getTitulo(), TAM_TITULO);
                escribirStringFijo(raf, n.getLink(), TAM_LINK);
                escribirStringFijo(raf, n.getFuente(), TAM_FUENTE);
                raf.writeLong(n.getFecha());

                contadorNuevas++;
            }

            System.out.println("Se han archivado " + contadorNuevas + " noticias nuevas.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<noticia> leerTodas() {
        List<noticia> lista = new ArrayList<>();
        File f = new File(FILE);
        if (!f.exists()) return lista;

        try (RandomAccessFile raf = new RandomAccessFile(FILE, "r")) {

            long registros = raf.length() / TAM_REGISTRO;

            for (int i = 0; i < registros; i++) {
                String titulo = leerStringFijo(raf, TAM_TITULO);
                String link = leerStringFijo(raf, TAM_LINK);
                String fuente = leerStringFijo(raf, TAM_FUENTE);
                long fecha = raf.readLong();

                lista.add(new noticia(titulo, link, fuente, fecha));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
