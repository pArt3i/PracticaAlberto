package main;

import java.io.Serializable;

public class noticia implements Serializable {

    private String titulo;
    private String link;
    private String fuente;
    private long fecha;

    public noticia(String titulo, String link, String fuente, long fecha) {
        this.titulo = titulo;
        this.link = link;
        this.fuente = fuente;
        this.fecha = fecha;
    }

    public String getTitulo() { return titulo; }
    public String getLink() { return link; }
    public String getFuente() { return fuente; }
    public long getFecha() { return fecha; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setLink(String link) { this.link = link; }
    public void setFuente(String fuente) { this.fuente = fuente; }
    public void setFecha(long fecha) { this.fecha = fecha; }

    @Override
    public String toString() {
        return "Noticia{" +
                "titulo='" + titulo + '\'' +
                ", link='" + link + '\'' +
                ", fuente='" + fuente + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
