package main;

import main.noticia;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LectorRSS {

    public List<noticia> leer(String urlFeed) {
        List<noticia> lista = new ArrayList<>();

        try {
            // Conectamos a la URL del RSS
            URL url = new URL(urlFeed);

            // Construimos el parser XML
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(url.openStream());

            doc.getDocumentElement().normalize();

            // Obtenemos todos los <item> del RSS
            NodeList items = doc.getElementsByTagName("item");

            // Recorremos cada item
            for (int i = 0; i < items.getLength(); i++) {
                Node nodo = items.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element item = (Element) nodo;

                    String titulo = obtener(item, "title");
                    String link = obtener(item, "link");
                    String fuente = urlFeed;
                    long fecha = System.currentTimeMillis();

                    lista.add(new noticia(titulo, link, fuente, fecha));
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
        return nl.item(0).getTextContent();
    }
}
