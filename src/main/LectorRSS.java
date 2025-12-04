package main;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LectorRSS {

    public List<noticia> leer(String urlFeed) {
        List<noticia> lista = new ArrayList<>();

        try {
            URL url = new URL(urlFeed);

            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(url.openStream());

            doc.getDocumentElement().normalize();

            NodeList items = doc.getElementsByTagName("item");

            for (int i = 0; i < items.getLength(); i++) {
                Node nodo = items.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element item = (Element) nodo;

                    String titulo = obtener(item, "title");
                    String link = obtener(item, "link");
                    String fuente = urlFeed;

                    String fechaStr = obtener(item, "pubDate");
                    long fechaLong;

                    if (!fechaStr.isEmpty()) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", java.util.Locale.ENGLISH);
                            fechaLong = sdf.parse(fechaStr).getTime();
                        } catch (Exception e) {
                            fechaLong = System.currentTimeMillis();
                        }
                    } else {
                        fechaLong = System.currentTimeMillis();
                    }

                    lista.add(new noticia(titulo, link, fuente, fechaLong));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    private String obtener(Element item, String etiqueta) {
        NodeList nl = item.getElementsByTagName(etiqueta);
        if (nl.getLength() == 0) return "";
        return nl.item(0).getTextContent().trim();
    }
}
