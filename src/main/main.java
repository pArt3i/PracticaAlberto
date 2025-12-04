package main;

import java.util.List;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        gestor gestorNoticias = new gestor();

        gestorNoticias.escribirLog("La aplicación se ha iniciado.");

        int opcion;

        do {
            System.out.println("===== ASISTENTE DIGITAL DE NOTICIAS =====");
            System.out.println("1. Actualizar noticias desde el feed");
            System.out.println("2. Generar informe HTML del día");
            System.out.println("3. Ver / Modificar URL del feed (config.ser)");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            while (!sc.hasNextInt()) {
                System.out.println("Entrada inválida. Introduzca un número:");
                sc.next();
            }

            opcion = sc.nextInt();
            sc.nextLine();
            System.out.println();

            switch (opcion) {

                case 1:
                    gestorNoticias.escribirLog("Seleccionada opción: Actualizar noticias");

                    String url = gestorNoticias.cargarURL();
                    System.out.println("Conectando a: " + url);
                    gestorNoticias.escribirLog("Conectando al feed: " + url);

                    List<noticia> nuevas = gestorNoticias.leerRSS(url);
                    System.out.println("Descargadas " + nuevas.size() + " noticias desde el feed.");
                    gestorNoticias.escribirLog("Descargadas " + nuevas.size() + " noticias desde el feed.");

                    gestorNoticias.guardarNoticias(nuevas);
                    gestorNoticias.escribirLog("Noticias actualizadas y guardadas en noticias.dat");

                    System.out.println("✔ Noticias actualizadas.");
                    break;

                case 2:
                    gestorNoticias.escribirLog("Seleccionada opción: Generar informe HTML");

                    List<noticia> todas = gestorNoticias.leerBD();
                    System.out.println("Generando informe HTML con las noticias de hoy...");
                    GeneradorHTML gen = new GeneradorHTML();
                    String ruta = gen.generar(todas);

                    gestorNoticias.escribirLog("Informe HTML generado en: " + ruta);
                    System.out.println("✔ Informe generado en: " + ruta);
                    break;

                case 3:
                    gestorNoticias.escribirLog("Seleccionada opción: Ver/Modificar URL (config.ser)");

                    // Leer lo que hay en config.ser
                    String urlActual = gestorNoticias.cargarURL();

                    System.out.println("Contenido actual de config.ser:");
                    System.out.println("URL del feed RSS = " + urlActual);
                    System.out.println();

                    System.out.print("¿Desea modificarla? (s/n): ");
                    String respuesta = sc.nextLine();

                    if (respuesta.equalsIgnoreCase("s")) {
                        System.out.print("Nueva URL: ");
                        String nuevaURL = sc.nextLine();
                        gestorNoticias.guardarURL(nuevaURL);

                        gestorNoticias.escribirLog("URL modificada a: " + nuevaURL);
                        System.out.println("✔ URL guardada en config.ser.");
                    } else {
                        System.out.println("No se realizaron cambios sobre config.ser.");
                    }
                    break;

                case 0:
                    gestorNoticias.escribirLog("La aplicación se ha cerrado por el usuario.");
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

            System.out.println();

        } while (opcion != 0);

        sc.close();
    }
}
