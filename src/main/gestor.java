package main;

import main.ArchivoNoticias;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class gestor {

    private final String CONFIG = "data/config.ser";
    private final String BD = "data/noticias.dat";
    private final String LOG = "data/resumen_diario.log";

    public gestor() {
        new File("data").mkdirs();
        new File("reports").mkdirs();
    }

    // =======================
    // CONFIGURACIÓN
    // =======================
    public String cargarURL() {
        File f = new File(CONFIG);
        if (!f.exists()) {
            guardarURL("https://www.xataka.com/feedburner.xml");
            return "https://www.xataka.com/feedburner.xml";
        }

        try (BufferedReader br = new BufferedReader(new FileReader(CONFIG))) {
            return br.readLine();
        } catch (Exception e) {
            return null;
        }
    }

    public void guardarURL(String url) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CONFIG))) {
            bw.write(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =======================
    // LECTURA RSS
    // =======================
    public List<noticia> leerRSS(String urlFeed) {
        LectorRSS lector = new LectorRSS();
        return lector.leer(urlFeed);
    }

    // =======================
    // GUARDAR NOTICIAS
    // =======================
    public void guardarNoticias(List<noticia> noticias) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(BD))) {
            oos.writeObject(noticias);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<noticia> leerBD() {
        File f = new File(BD);
        if (!f.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(BD))) {
            return (List<noticia>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // =======================
    // LOG
    // =======================
    public void escribirLog(String msg) {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(LOG, true))) {

            bw.write(java.time.LocalDateTime.now() + " - " + msg);
            bw.newLine();

        } catch (Exception e) {}
    }
}
