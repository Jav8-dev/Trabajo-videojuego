package io;

import java.util.Scanner;

public class MiEntradaSalida {

    // 1. El Scanner se hace PRIVADO para protegerlo.
    // Solo esta clase puede usarlo.
    private static Scanner sc = new Scanner(System.in);

    private MiEntradaSalida(){
        // Constructor privado para evitar instanciación
    }

    public static int solicitarEntero(String mensaje) {
        // Variable que almacenará el entero introducido por teclado.
        int numero = 0;
        // Variable que almacenará un booleano que indicará si se le debe volver a pedir el dato al usuario.
        boolean flag = true;

        while (flag) {
            // Pedimos el entero por pantalla.
            System.out.println(mensaje);
            // Comprobamos si el usuario está introduciendo algo correcto usando la excepción del método parseInt.
            try {
                numero = Integer.parseInt(sc.nextLine());
                // Si llegamos hasta aquí, es porque el usuario ha introducido un dato correcto.
                flag = false;
            }
            // Si se lanza la excepción, informamos al usuario de su error.
            catch (NumberFormatException e) {
                // 2. Mensaje de error específico.
                System.err.println("Error: Debe introducir un número entero.");
            }

        }

        return numero;
    }

    public static int solicitarEnteroPositivo(String mensaje) {
        // Variable que almacenará el entero introducido por teclado.
        int numero;
        do {
            numero = solicitarEntero(mensaje);
            if (numero < 0) {
                System.err.println("Error: El número debe ser positivo.");
            }
        } while (numero < 0);

        return numero;
    }

    public static int solicitarEnteroEnRango(String mensaje, int limiteInferior, int limiteSuperior) {
        // Variable que almacenará el entero introducido por teclado.
        int numero;

        do {
            numero = solicitarEntero(mensaje);
            if (numero < limiteInferior || numero > limiteSuperior) {
                System.err.printf("Error: El número debe estar entre %d y %d.%n", limiteInferior, limiteSuperior);
            }
        } while (numero < limiteInferior || numero > limiteSuperior);

        return numero;
    }

    public static char solicitarCaracter(String mensaje) {
        char c = '0';
        // Variable que almacenará un booleano que indicará si se le debe volver a pedir el dato al usuario.
        boolean flag = true;

        while (flag) {
            // Pedimos el carácter por pantalla.
            System.out.println(mensaje);

            try {
                // Obtenemos el primer carácter de la línea.
                c = sc.nextLine().charAt(0);
                // Si llegamos hasta aquí, es porque el usuario ha introducido un dato correcto.
                flag = false;
            }
            // Si se lanza la excepción (porque el usuario no escribió nada).
            catch (StringIndexOutOfBoundsException e) { // Es más específico que IndexOutOfBoundsException
                // 2. Mensaje de error específico.
                System.err.println("Error: No ha introducido ningún carácter. Inténtelo de nuevo.");
            }

        }

        return c;
    }

    public static char solicitarCaracterSN(String mensaje) {
        char c;
        do {
            c = Character.toUpperCase(solicitarCaracter(mensaje));
            if (c != 'S' && c != 'N') {
                System.out.println("Error: Debe introducir 'S' o 'N'.");
            }
        } while (c != 'S' && c != 'N');

        return c;
    }

    public static String solicitarCadena(String mensaje) {
        String cadena = "";
        // Variable que almacenará un booleano que indicará si se le debe volver a pedir el dato al usuario.
        boolean flag = true;

        while (flag) {
            // Pedimos el string por pantalla.
            System.out.println(mensaje);

            // 3. Usamos .trim() para eliminar espacios en blanco al inicio y al final.
            cadena = sc.nextLine().trim();

            // Comprobamos que la cadena no esté vacía después de quitarle los espacios.
            if (!cadena.isEmpty()) {
                // Si llegamos hasta aquí, el dato es correcto.
                flag = false;
            } else {
                // 2. Mensaje de error específico.
                System.err.println("Error: No puede introducir una cadena vacía.");
            }
        }

        return cadena;
    }

    /**
     * Muestra un mensaje por pantalla y solicita una cadena que debe
     * estar comprendida entre una serie de opciones válidas
     * Repite la operación hasta que se introduce una cadena válida.
     * @param mensaje El mensaje que se mostrará
     * @param opciones Las opciones aceptadas
     * @return La cadena introducida por el usuario
     */
    public static int seleccionarOpcion(String mensaje, String[] opciones) {
        System.out.println(mensaje);
        for (int i = 0; i < opciones.length; i++) {
            System.out.printf("%d. %s%n", i + 1, opciones[i]);
        }
        return solicitarEnteroEnRango("Seleccione una opción:", 1, opciones.length) - 1;
    }

    /**
     * Método que muestra un mensaje y simplemente espera que el usuario pulse enter
     * @param mensaje El mensaje que se mostrará
     */
    public static void getTecla(String mensaje) {
        System.out.print(mensaje);
        sc.nextLine();
    }

}