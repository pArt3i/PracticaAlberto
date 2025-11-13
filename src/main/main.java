package main;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = 1;
        do{
            System.out.println("--ASISTENTE DIGITAL DE NOTICIAS--");
            System.out.println("1. Actualizar noticias desde la feed");
            System.out.println("2. Generar informe HTML del dia");
            System.out.println("3. Ver/Modificar URL el feed");
            System.out.println("0. Salir");
            opcion = sc.nextInt();
            switch (opcion){
                case 1:
                    System.out.println("1");
                    break;
                case 2:
                    System.out.println("2");
                    break;
                case 3:
                    System.out.println("3");
                    break;
                case 0:
                    System.out.println("Salir");
                    break;

            }
        }while(opcion!=0);

    }
}
