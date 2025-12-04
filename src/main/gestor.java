package main;

import java.io.*;
import java.util.List;

public class gestor {

    // data dentro de src
    private static final String CONFIG = "src/data/config.ser";
    private static final String LOG = "src/data/resumen_diario.log";

    public gestor() {
        // crear src/data y reports en la raíz del proyecto
        File dataDir = new File("src/data");
        File reportsDir = new File("reports");

        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }
    }

    // ==========================
    // CONFIG SER (serializado)
    // ==========================
    public String cargarURL() {
        File f = new File(CONFIG);

        if (!f.exists()) {
            guardarURL("https://www.xataka.com/feedburner.xml");
            return "https://www.xataka.com/feedburner.xml";
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CONFIG))) {
            Object obj = ois.readObject();
            if (obj instanceof String) {
                return (String) obj;
            } else {
                f.delete();
                guardarURL("https://www.xataka.com/feedburner.xml");
                return "https://www.xataka.com/feedburner.xml";
            }
        } catch (IOException | ClassNotFoundException e) {
            f.delete();
            guardarURL("https://www.xataka.com/feedburner.xml");
            return "https://www.xataka.com/feedburner.xml";
        }
    }

    public void guardarURL(String url) {
        File dirData = new File("src/data");
        if (!dirData.exists()) dirData.mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CONFIG))) {
            oos.writeObject(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ==========================
    // RSS
    // ==========================
    public List<noticia> leerRSS(String urlFeed) {
        LectorRSS lector = new LectorRSS();
        return lector.leer(urlFeed);
    }

    // ==========================
    // BASE DE DATOS RANDOM
    // ==========================
    public void guardarNoticias(List<noticia> noticias) {
        ArchivoNoticias archivo = new ArchivoNoticias();
        archivo.guardarNuevas(noticias);
    }

    public List<noticia> leerBD() {
        ArchivoNoticias archivo = new ArchivoNoticias();
        return archivo.leerTodas();
    }

    // ==========================
    // LOG
    // ==========================
    public void escribirLog(String msg) {
        File dirData = new File("src/data");
        if (!dirData.exists()) dirData.mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOG, true))) {
            String linea = java.time.LocalDateTime.now() + " - " + msg;
            bw.write(linea);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
