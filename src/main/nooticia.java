package main;

import java.io.Serializable;

public class nooticia implements Serializable {
    private String Titulo;
    private String Link;
    private String Fuente;
    private long Fecha;

    public nooticia(String titulo, String link, String fuente, long fecha) {
        Titulo = titulo;
        Link = link;
        Fuente = fuente;
        Fecha = fecha;
    }
}
