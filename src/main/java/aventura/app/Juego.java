package aventura.app;

import domain.*;
import interfaces.Abrible;
import interfaces.Leible;

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
    private static Objeto[][] objetosMapa = {
            {
                    null,
                    new Nota("nota", "Una nota amarillenta", "Pista: El código de la caja fuerte es A-101"),
                    new Contenedor("cajon", "Un cajón de madera viejo", null)
            },
            {
                    new Palo("palo", "Un palo de madera resistente"),
                    new Trapo("trapo", "Un trapo viejo y sucio")
            },
            {
                    new Llave("llave", "Una llave dorada", "A-101"),
                    null
            },
            {
                    new Contenedor("cofre", "Un cofre del tesoro cerrado", "A-101",
                            new Item("diamante", "Un diamante brillante")),
                    null
            }
    };

    // El inventario del jugador.
    private static Jugador jugador = new Jugador();


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
        System.out.println(habitaciones[jugador.getHabitacionActual()].getDescripcion());


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
                    for (Objeto objeto : jugador.getInventario()) {
                        if (objeto != null) {
                            System.out.print(objeto.getNombre() + " ");
                        }
                    }
                    System.out.println();
                }
                case "ir izquierda" -> {
                    if (jugador.getHabitacionActual() > 0) {
                        jugador.setHabitacionActual(jugador.getHabitacionActual() -1) ;
                        System.out.println("Te has movido a la habitación de la izquierda.");
                        mostrarInfoHabitacion();
                    } else {
                        System.out.println("No puedes ir más a la izquierda.");
                    }
                }
                case "ir derecha" -> {
                    if (jugador.getHabitacionActual() < habitaciones.length - 1) {
                        jugador.setHabitacionActual(jugador.getHabitacionActual() +1);
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

                case "abrir" -> {
                    System.out.println("Objetos que puedes intentar abrir: ");
                    boolean hayAbribles = false;

                    for (Objeto obj : objetosMapa[jugador.getHabitacionActual()]) {
                        if (obj instanceof Abrible) {
                            System.out.println("_ " + obj.getNombre());
                            hayAbribles = true;
                        }
                    }

                    if (!hayAbribles) {
                        System.out.println("No hay nada que abrir");
                        break;
                    }

                    System.out.println("Que quieres abrir?");
                    String nombreObjeto = scanner.nextLine().toLowerCase(Locale.ROOT);

                    Objeto objeto = buscarObjeto(nombreObjeto);

                    if (objeto == null) {
                        System.out.println("No hay ningún objeto llamado " + nombreObjeto + " aquí.");
                    } else if (!(objeto instanceof Abrible abrible)) {
                        System.out.println("No puedes abrir " + nombreObjeto + ".");
                    } else {
                        Llave llaveNecesaria = null;
                        if (objeto instanceof Contenedor contenedor) {
                            // Buscar llave que funcione
                            for (Objeto inventario : jugador.getInventario()) {
                                if (inventario instanceof Llave llave) {
                                    llaveNecesaria = llave;
                                    break;
                                }
                            }
                        }

                        RespuestaAccion respuesta = abrible.abrir(llaveNecesaria);
                        System.out.println(respuesta.mensaje());

                        if (respuesta.esExito() && objeto instanceof Contenedor contenedor) {
                            Objeto objetoDentro = contenedor.sacarObjeto();
                            if (objetoDentro != null) {
                                if (jugador.agregarAlInventario(objetoDentro)) {
                                    System.out.println("Has cogido el " + objetoDentro.getNombre() + " automaticamente");
                                } else {
                                    System.out.println("Tu inventario esta lleno. El " + objetoDentro.getNombre() + " sigue en el " + contenedor.getNombre() + ".");
                                }
                            }
                        }
                    }
                }
                case "examinar" -> {
                    System.out.print("¿Que objeto quieres examinar? ");
                    String nombreObjeto = scanner.nextLine().toLowerCase(Locale.ROOT);

                    Objeto objeto = buscarObjeto(nombreObjeto);

                    if (objeto == null) {
                        System.out.println("El objeto " + nombreObjeto + " no existe (al menos aqui).");
                    } else {
                        System.out.println(objeto.getDescripcion());

                        // Pattern Matching - Si es algo que se puede leer
                        if (objeto instanceof Leible leible) {
                            System.out.println("-----------------------------");
                            System.out.println("Lees: " + leible.leer());
                            System.out.println("-----------------------------");
                        }

                        // Si es un contenedor, mostrar si está abierto o cerrado
                        if (objeto instanceof Contenedor contenedor) {
                            if (contenedor.estaAbierto()) {
                                if (contenedor.getObjetoGuardado() != null) {
                                    System.out.println("Dentro hay: " + contenedor.getObjetoGuardado().getNombre());
                                } else {
                                    System.out.println("Esta vacío.");
                                }
                            } else {
                                System.out.println("Esta cerrado.");
                            }
                        }
                    }
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
        System.out.println("Examinar: examina un objeto en detalle");
        System.out.println("Inventario: muestra los objetos que llevas contigo");
        System.out.println("Coger: intenta coger un objeto de la habitación actual");
        System.out.println("Abrir: abre un contenedor (usa llaves automáticamente)");
        System.out.println("Salir: termina el juego");
    }

    /**
     * Muestra la información de la habitación actual,
     * incluyendo su descripción y los objetos que hay en ella.
     */
    private static void mostrarInfoHabitacion() {
        System.out.println(habitaciones[jugador.getHabitacionActual()].getDescripcion());

        boolean hayObjetos = false;

        for (Objeto objeto : objetosMapa[jugador.getHabitacionActual()]) {
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
        for (int i = 0; i < objetosMapa[jugador.getHabitacionActual()].length; i++) {
            if (objetosMapa[jugador.getHabitacionActual()][i] != null && objetoACoger.equals(objetosMapa[jugador.getHabitacionActual()][i].getNombre())) {
                objetoEncontrado = true;
                // Buscar espacio en el inventario
                if (jugador.agregarAlInventario(objetosMapa[jugador.getHabitacionActual()][i])) {
                    System.out.println("Has pillado " + objetoACoger + ".");
                    objetosMapa[jugador.getHabitacionActual()][i] = null; // Quitar de la habitación
                } else {
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
        for (Objeto objeto : objetosMapa[jugador.getHabitacionActual()]) {
            if (objeto != null) {
                hayObjetos = true;
                System.out.print(hayMasDeUnObjeto ? ", " + objeto.getNombre() : objeto.getNombre());
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
        for (Objeto objeto : objetosMapa[jugador.getHabitacionActual()]) {
            if (objeto != null) {
                return true;
            }
        }
        return false;
    }
    /**
     * Vamos a crear el metodo que busqie un objeto por nombre de la habitacion actual o en el inventario
     *
     */
    private static Objeto buscarObjeto(String nombre) {
        //Buscamos pirmero en la habitacion actual
        for (Objeto obj : objetosMapa[jugador.getHabitacionActual()]) {
            if (obj != null && obj.getNombre().equalsIgnoreCase(nombre)) {
                return obj;
            }
        }
        //Si no, que mire en el invetario
        return jugador.buscarEnInventario(nombre);
    }

    private static Llave buscarLLaveEnInventario(String codigo) {
        for (Objeto obj : jugador.getInventario()) {
            if (obj instanceof Llave llave) {
                if (llave.getCodigoSeguridad().equals(codigo)) {
                    return llave;
                }
            }
        }
        return null;
    }
}
