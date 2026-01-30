package aventura.app;

import domain.Habitacion;

import java.util.Locale;
import java.util.Scanner;

/**
 * Clase principal del juego "Tu Propia Aventura".
 * Esqueleto para la Misión 1 (UD1-UD3).
 * VUESTRO TRABAJO es rellenar todos los TODO
 */
public class Juego {

    // --- NÚCLEO: Definición de Datos (FASE 1) ---
    // Esta parte os la damos HECHA. Es el "contrato" del núcleo.

    private static String descripcionJuego = "Estabas caminando por el matapiojos tranquilamente por la noche, de repente te intentan asaltar, " +
            "huyendo tropiezas y te das un duro golpe en la cabeza, piensas \"me va a salir un gran chichon\", pero de la nada caes inconsciente, " +
            "despiertas en un sitio tetrico, este sitio parece un laberinto sin final, las paredes son del mismo color y la iluminacion es tenue y calurosa, " +
            "¿Que vas a hacer ahora?";

    // El mapa de habitaciones.
    // TODO: (Skin) ¡Rellenad esto con vuestras descripciones!
    private static Habitacion[] habitaciones = {
        new Habitacion("Inicio", "Estas en el inicio, hay puertas a la: IZQUIERDA, DERECHA y hay una nota en la mesa."),
        new Habitacion("Habitacion 1", "Estas en la habitacion 1. Hay puertas a la IZQUIERDA, DERECHA."),
        new Habitacion("Habitacion 2", "Estas en la habitacion 2. Hay una puerta a la DERECHA y has visto una llave en una mesa."),
        new Habitacion("Habitacion 3", "Estas en la habitacion 3. Hay puertas a la IZQUIERDA, DERECHA y has visto una llave dentro de un jarron."),
        new Habitacion("Habitacion 4", "Estas en la habitacion 4. Hay una puerta a la IZQUIERDA")
    };


    // Los objetos que hay en cada habitación.
    // TODO: (Skin) Rellenad esto con vuestros objetos
    private static String[][] objetosMapa = {
            {null, "nota"},           // Objetos en Habitación 0
            {null, null},           // Objetos en Habitación 1
            {"llave", null},    // Objetos en Habitación 2
            {"llave", null}
    };

    // El inventario del jugador. Tamaño fijo.
    private static String[] inventario = new String[5];

    // Variable que guarda la posición actual del jugador
    private static int habitacionActual = 0; // Empezamos en la primera habitación

    // --- FIN DE LA DEFINICIÓN DE DATOS ---


    public static void main(String[] args) {
        // Puedes utilizar la clase MiEntradaSalida, que viviría en el paquete io
        Scanner scanner = new Scanner(System.in);
        boolean jugando = true;

        System.out.println("¡Bienvenido a BackDoors");
        System.out.println("------------------------------------------");

        // TODO 1a: Muestra la descripción general del juego
        System.out.println(descripcionJuego);

        // TODO 1b: Muestra la descripción de la primera habitación
        Pista:
        System.out.println(habitaciones[habitacionActual].getDescripcion());


        // TODO 2: Iniciar el bucle principal del juego (game loop)
        while (jugando) {

            // TODO 3: Leer el comando del usuario por teclado
            System.out.print("\n> ");
            String comando = scanner.nextLine().toLowerCase(Locale.ROOT);

            /*
            TODO 4: Crear un 'switch' o una estructura 'if-else if'
             para procesar el 'comando' del usuario.
             Debe gestionar como mínimo: "ayuda", "mirar", "inventario",
             "ir derecha", "ir izquierda", "coger [objeto]" y "salir".
             */

            switch (comando) {
                case "mirar" -> {
                    mostrarInfoHabitacion();
                }
                case "inventario" -> {
                    System.out.print("Objetos en tu inventario: ");
                    for (String objeto : inventario) {
                        if (objeto != null) {
                            System.out.print(objeto + " ");
                        }
                    }
                    System.out.println();
                }
                case "ir izquierda" -> {
                    if (habitacionActual > 0) {
                        habitacionActual--;
                        System.out.println("Te has movido a la habitación de la izquierda.");
                        mostrarInfoHabitacion();
                    } else {
                        System.out.println("No puedes ir más a la izquierda.");
                    }
                }
                case "ir derecha" -> {
                    if (habitacionActual < habitaciones.length - 1) {
                        habitacionActual++;
                        System.out.println("Te has movido a la habitación de la derecha.");
                        mostrarInfoHabitacion();
                    } else {
                        System.out.println("No puedes ir más a la derecha.");
                    }
                }
                case "coger" -> {
                    if (!hayObjetosEnHabitacion()) {
                        System.out.println("No hay objetos para coger en esta habitación.");
                        break;
                    }
                    mostrarObjetosHabitacion();
                    System.out.print("¿Qué objeto quieres coger? ");
                    String objetoACoger = scanner.nextLine().toLowerCase(Locale.ROOT);

                    procesarComandoCoger(objetoACoger);
                }
                case "salir" -> {
                    jugando = false;
                    System.out.println("Saliendo del juego...");
                }

                default -> {
                    mostrarAyuda();
                }
            }


        }

        System.out.println("¡Gracias por jugar!");
        scanner.close();
    }

    /**
     * Muestra la ayuda con los comandos disponibles.
     */
    private static void mostrarAyuda() {
        System.out.println("Estos son los comandos que puedes ejecutar:");
        System.out.println("Ir derecha: intenta ir hacia la izquierda");
        System.out.println("Ir izquierda: intenta ir hacia la derecha");
        System.out.println("Mirar: muestra la descripción de la habitación actual y los objetos que hay en ella");
        System.out.println("Inventario: muestra los objetos que llevas contigo");
        System.out.println("Coger [objeto]: intenta coger un objeto de la habitación actual");
        System.out.println("Salir: termina el juego");
    }

    /**
     * Muestra la información de la habitación actual,
     * incluyendo su descripción y los objetos que hay en ella.
     */
    private static void mostrarInfoHabitacion() {
        System.out.println(habitaciones[habitacionActual].getDescripcion());

        boolean hayObjetos = false;

        for (String objeto : objetosMapa[habitacionActual]) {
            if (objeto != null) {
                hayObjetos = true;
                break;
            }
        }

        if (!hayObjetos) {
            System.out.println("No hay objetos en esta habitación.");
        }
        else {
            mostrarObjetosHabitacion();
        }

    }

    /**
     * Procesa el comando de coger un objeto.
     *
     * @param objetoACoger El nombre del objeto que se quiere coger.
     */
    private static void procesarComandoCoger(String objetoACoger) {
        boolean objetoEncontrado = false;
        for (int i = 0; i < objetosMapa[habitacionActual].length; i++) {
            if (objetoACoger.equals(objetosMapa[habitacionActual][i])) {
                objetoEncontrado = true;
                // Buscar espacio en el inventario
                boolean espacioEncontrado = false;
                for (int j = 0; j < inventario.length; j++) {
                    if (inventario[j] == null) {
                        inventario[j] = objetoACoger;
                        objetosMapa[habitacionActual][i] = null; // Quitar el objeto de la habitación
                        System.out.println("Has cogido " + objetoACoger + ".");
                        espacioEncontrado = true;
                        break;
                    }
                }
                if (!espacioEncontrado) {
                    System.out.println("Tu inventario está lleno. No puedes coger más objetos.");
                }
                break;
            }
        }
        if (!objetoEncontrado) {
            System.out.println("No hay ningún objeto llamado " + objetoACoger + " en esta habitación.");
        }
    }

    /**
     * Muestra los objetos que hay en la habitación actual.
     */
    private static void mostrarObjetosHabitacion() {
        System.out.print("Objetos en la habitación: ");
        boolean hayObjetos = false;
        boolean hayMasDeUnObjeto = false;
        for (String objeto : objetosMapa[habitacionActual]) {
            if (objeto != null) {
                hayObjetos = true;
                System.out.print(hayMasDeUnObjeto ? ", " + objeto : objeto);
                hayMasDeUnObjeto = true;
            }
        }
        if (!hayObjetos) {
            System.out.print("No hay objetos.");
        }
        System.out.println();
    }

    /**
     * Comprueba si hay objetos en la habitación actual.
     *
     * @return true si hay al menos un objeto, false en caso contrario.
     */
    private static boolean hayObjetosEnHabitacion() {
        for (String objeto : objetosMapa[habitacionActual]) {
            if (objeto != null) {
                return true;
            }
        }
        return false;
    }

}