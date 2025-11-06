package aventura.app;

import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

/**
 * Clase principal del juego "Tu Propia Aventura".
 * Esqueleto para la Misión 1 (UD1-UD3).
 * VUESTRO TRABAJO es rellenar todos los
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
    private static String[] habitaciones = {
            "Estas en el inicio hay puertas a la: IZQUIERDA, DERECHA Y DELANTE y hay una nota en la mesa.",  // Posición 0
            "Estás en la habitacion 1. Hay puertas a la IZQUIERDA, DERECHA Y ATRAS.", // Posición 1
            "Estás en la habitacion 2. Hay una puerta a la DERECHA y has visto una 'llave' en una mesa.", // Posición 2
            "Estás en la habitacion 3. Hay una puerta a la IZQUIERDA y has visto una 'llave' dentro de un jarron.", // Posición 3
            "Estás en la habitacion 4. Has conseguidor escapar de las backrooms", // Posición 4
            "Estás en la habitacion 5. Hay una puerta a la IZQUIERDA y ATRAS.", // Posición 5
            "Estás en la habitacion 6. Hay una puerta HACIA DELANTE y has visto una 'llave' detras de un cuadro..", // Posición 6
            // HE CREADO LAS HABITACIONES POR EL MOMENTO, PARA TENER UNA VISTA PREVIA
    };

    // Los objetos que hay en cada habitación.
    // TODO: (Skin) Rellenad esto con vuestros objetos
    private static String[][] objetosMapa = {
            {"nota", null},           // Objetos en Habitación 0
            {null, null},           // Objetos en Habitación 1
            {"llave", null},      // Objetos en Habitación 2
            {"llave", null},      // Objetos en Habitación 3
            {null, null},      // Objetos en Habitación 4
            {null, null},      // Objetos en Habitación 5
            {"llave", null},      // Objetos en Habitación 6
    };


    // El inventario del jugador. Tamaño fijo.
    private static String[] inventario = new String[4];

    // Variable que guarda la posición actual del jugador
    private static int habitacionActual = 0; // Empezamos en la primera habitación

    // --- FIN DE LA DEFINICIÓN DE DATOS ---


    public static void main(String[] args) {
        // Puedes utilizar la clase MiEntradaSalida, que viviría en el paquete io
        Scanner scanner = new Scanner(System.in);
        boolean jugando = true;


        System.out.println("¡Bienvenido a 'BackroomsDoor'!");
        System.out.println("------------------------------------------");

        // TODO 1a: Muestra la descripción general del juego
        System.out.println(descripcionJuego);

        // TODO 1b: Muestra la descripción de la primera habitación
        // Pista: System.out.println(habitaciones[...]);
        System.out.println(habitaciones[habitacionActual]);


        // TODO 2: Iniciar el bucle principal del juego (game loop)
        while (jugando) {

            // TODO 3: Leer el comando del usuario por teclado
            System.out.print("\n> ");
            scanner.useDelimiter("\n"); //Acepta espacios en el scanner
            //String comando = ...;
            String comando = scanner.next().toLowerCase();


            /*
            TODO 4: Crear un 'switch' o una estructura 'if-else if'
             para procesar el 'comando' del usuario.
             Debe gestionar como mínimo: "ayuda", "mirar", "inventario",
             "ir derecha", "ir izquierda", "coger [objeto]" y "salir".
             */
            switch (comando) {
                    case "coger":
                        System.out.println("Especifica el objeto que quieres coger:");
                        mirarAlrededor();
                        String objeto = scanner.next();
                        cogerObjeto(objeto);
                        break;
                    case "ayuda": //Panel de ayuda para los comandos
                        mostrarAyuda();
                        break;
                    case "ir derecha": //Comando para ir a la derecha
                        irDerecha();
                        break;
                    case "ir izquierda": //Comando para ir a la izquierda
                        irIzquierda();
                        break;
                    case "ir delante": //Comando para ir hacia delante
                        irDelante();
                        break;
                    case "ir atras": //Comando para hacia atras
                        irAtras();
                        break;
                    case "inventario":
                        mostrarInventario();
                        break;
                    case "mirar":
                        mirarAlrededor();
                        break;
                    case "salir":
                        System.out.println("Saliendo del juego...");
                        jugando = false;
                        break;
                    default:
                        System.out.println("Ese comando no existe escribe 'ayuda' para ver los comandos disponibles. ");
                }
        }
        System.out.println("¡Gracias por jugar!");
        scanner.close();


    }

    private static void cogerObjeto(String objeto) {
        boolean encontrado = false;

        //Recorremos todas las columnas de los objetos del mapa de la habitación actual
        for (int i = 0; i < objetosMapa[habitacionActual].length; i++) {
            String objetoActual = objetosMapa[habitacionActual][i];

            if (objetoActual != null && objetoActual.equals(objeto)) { //Comprobamos que el objeto introdu
                encontrado = true;

                //Buscar si hay algun hueco en el inventario
                for (int j = 0; j < inventario.length; j++) {
                    if (inventario[j] == null) { //Comprobar si hay algun hueco vacio en el inventario
                        inventario[j] = objeto; // Añadir al inventario
                        objetosMapa[habitacionActual][i] = null; // Eliminar del mapa el objeto que hemos cogido
                        System.out.println("Has cogido la " + objeto + ".");

                        if (objeto.equals("llave")) { //Comprobar si el objeto es una llave.
                            llavesTotal++; //Sumar al contador de llaves una mas
                            System.out.println("Llevas un total de " + llavesTotal + " llave."); //Mostrar las llaves que tenemos actualmente
                        } else if (objeto.equals("nota")) { //Comprobar si el objeto es una nota
                            System.out.println("Estas atrapado, tu objetivo es conseguir todas las llaves de este lugar para poder salir HAHAHAHA");
                        }

                        return;
                    }
                }

                System.out.println("Tu inventario está lleno. No puedes coger más objetos."); //Si no hay hueco en el inventario mostramos este mensaje
                return;
            }
        }

        if (!encontrado) {
            System.out.println("No hay ningún objeto llamado '" + objeto + "' en esta habitación."); //Si no hay ningun objeto en la habitacion con este nombre mostramos este mensaje
        }
    }
    private static void mirarAlrededor() {
        boolean hayObjetos = false;
        for (int i = 0; i < objetosMapa[habitacionActual].length; i++) {
            String objetoActual = objetosMapa[habitacionActual][i];
            if (objetoActual != null) {
                System.out.println("Es la sala actual hay una: " + objetoActual);
                hayObjetos = true;
            }
        }
        if (!hayObjetos) {
            System.out.println("No hay ningun obejeto en esta sala.");
        }
    }
    private static int llavesTotal = 0;

    private static void mostrarAyuda() {
        System.out.println("================================= AYUDA =================================");
        System.out.println("Lista de comandos disponibles:");
        System.out.println(" - ayuda: Muestra esta lista de comandos.");
        System.out.println(" - mirar: Observa el entorno actual y describe lo que ves.");
        System.out.println(" - inventario: Muestra los objetos que llevas contigo.");
        System.out.println(" - ir derecha: Te mueves hacia la derecha (si es posible).");
        System.out.println(" - ir izquierda: Te mueves hacia la izquierda (si es posible).");
        System.out.println(" - ir delante: Te mueves hacia delante (si es posible).");
        System.out.println(" - ir atras: Te mueves hacia detras (si es posible).");
        System.out.println(" - coger : Recoge un objeto del entorno.");
        System.out.println(" - salir: Termina la partida.");
        System.out.println("==========================================================================");
    }


    private static void irDerecha() {
        switch (habitacionActual) {
            case 0 -> mover(5);
            case 1 -> mover(3);
            case 2 -> mover(1);
            case 4 -> mover(0);
            default -> System.out.println("No puedes ir hacia la derecha desde esta habitacion.");
        }
    }

    private static void irIzquierda() {
        switch (habitacionActual) {
            case 0 -> {
                if (llavesTotal >= 3) {
                    mover(4);
                } else {
                    System.out.println("SALA BLOQUEADA. Tienes: " + llavesTotal + "/3 llaves");
                }
            }
            case 1 -> mover(2);
            case 3 -> mover(1);
            case 5 -> mover(0);
            default -> System.out.println("No puedes ir hacia la izquierda desde aquí.");
        }
    }
    private static void irDelante() {
        switch (habitacionActual) {
            case 0 -> mover(1);
            case 6 -> mover(5);
            default -> System.out.println("No puedes ir hacia delante desde aquí.");
        }
    }

    private static void irAtras() {
        switch (habitacionActual) {
            case 1 -> mover(0);
            case 5 -> mover(6);
            default -> System.out.println("No puedes ir hacia atrás desde aquí.");
        }
    }
    private static void mover(int nuevaHabitacion) {
        System.out.println("Te mueves hacia la nueva habitación...");
        habitacionActual = nuevaHabitacion;
        System.out.println(habitaciones[habitacionActual]);
    }


    private static void eventoProbable() {
        Random random = new Random();
        boolean hayBicho = false;
        //este boolean nos servira para cuando lo llamemos tengamos la probabilidad de un bicho
        int numrand = random.nextInt(100); // la probabilidad
        if (numrand < 30) {  // le ponemos el 30%
            System.out.println("CORRE, EL BICHO ESTA EN ESTA HABITACION, TIENES 4 SEGUNDOS PARA HUIR"); //esto es cambiable
            hayBicho = true;
        }
    }
    private static void mostrarInventario() {
        System.out.println("Inventario:");
        boolean vacio = true;
        for (String objeto : inventario) {
            if (objeto != null) {
                System.out.println(" - " + objeto);
                vacio = false;
            }
        }
        if (vacio) System.out.println("Tu inventario está vacío.");
    }
    /*
    (Opcional - Buenas Prácticas)
    Si el 'switch' se vuelve muy grande, podéis crear métodos privados
    para organizar el código, por ejemplo:
    private static void procesarComandoCoger(String comando) { ... }
    private static void mostrarInfoHabitacion() { ... }
    */
}