package main;

import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        gestor gestorNoticias = new gestor();
        int opcion;
        do {
            System.out.println("--ASISTENTE DIGITAL DE NOTICIAS--");
            System.out.println("1. Actualizar noticias desde el feed");
            System.out.println("2. Generar informe HTML del día");
            System.out.println("3. Ver/Modificar URL del feed");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    String url = gestorNoticias.cargarURL();
                    System.out.println("Conectando a: " + url);
                    List<noticia> nuevas = gestorNoticias.leerRSS(url);
                    gestorNoticias.guardarNoticias(nuevas);
                    gestorNoticias.escribirLog("Noticias actualizadas desde " + url);
                    System.out.println("Noticias actualizadas y guardadas.");
                    break;

                case 2:
                    // Esta parte requerirá implementar el generador HTML
                    // gestorNoticias.generarInformeHTML();
                    gestorNoticias.escribirLog("Informe HTML diario generado");
                    System.out.println("Informe HTML del día generado (implementa el método).");
                    break;

                case 3:
                    String urlActual = gestorNoticias.cargarURL();
                    System.out.println("URL actual del feed: " + urlActual);
                    System.out.print("¿Desea modificarla? (s/n): ");
                    String respuesta = sc.nextLine();
                    if (respuesta.equalsIgnoreCase("s")) {
                        System.out.print("Introduzca nueva URL del feed: ");
                        String nuevaURL = sc.nextLine();
                        gestorNoticias.guardarURL(nuevaURL);
                        gestorNoticias.escribirLog("URL del feed modificada a " + nuevaURL);
                        System.out.println("URL guardada correctamente.");
                    } else {
                        System.out.println("No se realizaron cambios.");
                    }
                    break;

                case 0:
                    gestorNoticias.escribirLog("Aplicación finalizada por el usuario");
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
            System.out.println();
        } while(opcion != 0);

        sc.close();
    }
}
