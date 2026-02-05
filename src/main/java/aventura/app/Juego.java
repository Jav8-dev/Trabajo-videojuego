package aventura.app;

import domain.*;
import exceptions.ObjetosException;
import interfaces.Abrible;
import interfaces.Combinable;
import interfaces.Inventariable;
import interfaces.Leible;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

/**
 * Clase principal del juego "Tu Propia Aventura".
 * Esqueleto para la Misión 1 (UD1-UD3).
 * VUESTRO TRABAJO es rellenar todos los TODO
 */
public class Juego {

    /**
     * Descripción general del juego que se muestra al inicio.
     */
    private static String descripcionJuego = "Estabas caminando por el matapiojos tranquilamente por la noche, de repente te intentan asaltar, " +
            "huyendo tropiezas y te das un duro golpe en la cabeza, piensas \"me va a salir un gran chichon\", pero de la nada caes inconsciente, " +
            "despiertas en un sitio tetrico, este sitio parece un laberinto sin final, las paredes son del mismo color y la iluminacion es tenue y calurosa, " +
            "¿Que vas a hacer ahora?";

    /**
     * Array de habitaciones que forman el mapa del juego.
     * Cada Habitacion tiene un nombre y una descripción.
     */
    private static Habitacion[] habitaciones = {
            new Habitacion("Inicio", "Estas en el inicio, hay puertas a la: IZQUIERDA, DERECHA y hay una nota en la mesa y un cajon."),
            new Habitacion("Habitacion 1", "Estas en la habitacion 1. Hay puertas a la IZQUIERDA, DERECHA, tambien ves un palo (¿Servira para algo?)."),
            new Habitacion("Habitacion 2", "Estas en la habitacion 2. Hay una puerta a la DERECHA y has visto una llave en una mesa."),
            new Habitacion("Habitacion 3", "Estas en la habitacion 3. Hay puertas a la IZQUIERDA, DERECHA y has visto un cofre cerrado."),
            new Habitacion("Habitacion 4", "Estas en la habitacion 4. Hay una puerta a la IZQUIERDA")
    };


    /**
     * Matriz de objetos del mapa.
     * Cada fila representa una habitación,
     * cada columna un posible objeto en esa habitación.
     */
    // TODO: (Skin) Rellenad esto con vuestros objetos
    private static Objeto[][] objetosMapa = {
            {null, new Nota("nota", "Una nota con el codigo", "Pista: El codigo de la caja fuerte es A-101"), new Contenedor("cajon", "Un cajon de madera viejo", null)}, // Objetos en Habitación 0
            {new Palo("palo","Palo: Se puede usar para crear objetos"), null}, // Objetos en Habitación 1
            {new Llave("llave", "Una llave dorada para abrir un cofre", "A-101"), null}, // Objetos en Habitación 2
            {new Contenedor("cofre", "Un cofre del tesoro con objetos", "A-101", // Objetos en Habitación 3
                    new Cuchilla("cuchilla", "Cuchilla: Se puede usar para crear objetos"))},
            {new Llave("llave", "llave final", "1332"), null} // Objetos en Habitación 4
    };

    /**
     * Único jugador de la partida.
     * Aquí se almacena su inventario y la habitación actual.
     */
    private static Jugador jugador = new Jugador();


    // --- FIN DE LA DEFINICIÓN DE DATOS ---


    public static void main(String[] args){
        // Scanner para leer comandos escritos por el usuario en consola.
        Scanner scanner = new Scanner(System.in);
        boolean jugando = true;

        System.out.println("¡Bienvenido a BackDoors");
        System.out.println("------------------------------------------");

     // Muestra la descripción general del juego.
        System.out.println(descripcionJuego);

        // Muestra la descripción de la primera habitación donde empieza el jugador.
        System.out.println(habitaciones[jugador.getHabitacionActual()].getDescripcion());


        // Bucle principal del juego: se repite hasta que el jugador escriba "salir".
        while (jugando) {
            // Leer el comando completo que escriba el usuario.
            System.out.print("\n> ");
            String comando = scanner.nextLine().toLowerCase(Locale.ROOT);

            /*
             * Procesa el comando del usuario usando un switch de cadenas.
             * Cada case corresponde a una acción del juego.
             */

            switch (comando) {
                case "mirar" -> {
                        mostrarInfoHabitacion();
                        }
                // Muestra info de la habitación actual y los objetos que hay en ella.
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
                    // Intenta mover al jugador una habitación a la izquierda.
                    if (jugador.getHabitacionActual() > 0) {
                        jugador.setHabitacionActual(jugador.getHabitacionActual() -1) ;
                        System.out.println("Te has movido a la habitación de la izquierda.");
                        mostrarInfoHabitacion();
                    } else {
                        System.out.println("No puedes ir más a la izquierda.");
                    }
                }
                case "ir derecha" -> {
                    // Intenta mover al jugador una habitación a la derecha.
                    if (jugador.getHabitacionActual() < habitaciones.length - 1) {
                        jugador.setHabitacionActual(jugador.getHabitacionActual() +1);
                        System.out.println("Te has movido a la habitación de la derecha.");
                        mostrarInfoHabitacion();
                    } else {
                        System.out.println("No puedes ir más a la derecha.");
                    }
                }
                case "coger" -> {
                    // Permite al jugador coger un objeto de la habitación actual.
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
                    // Lógica del comando ABRIR: muestra qué se puede abrir y llama a Abrible.abrir().
                    System.out.println("Objetos que puedes intentar abrir: ");
                    boolean hayAbribles = false;

                    // Lista objetos Abrible de la habitación actual.
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
                    // Busca el objeto tanto en la habitación como en el inventario.
                    System.out.println("Que quieres abrir?");
                    String nombreObjeto = scanner.nextLine().toLowerCase(Locale.ROOT);

                    Objeto objeto = buscarObjeto(nombreObjeto);

                    if (objeto == null) {
                        System.out.println("No hay ningún objeto llamado " + nombreObjeto + " aquí.");
                    } else if (!(objeto instanceof Abrible abrible)) {
                        System.out.println("No puedes abrir " + nombreObjeto + ".");
                    } else {
                        // Si es Abrible, intentamos pasarle una llave apropiada (si la hay).
                        Llave llaveNecesaria = null;
                        if (objeto instanceof Contenedor contenedor) {
                            // Busca la primera llave del inventario que pueda servir.
                            for (Objeto inventario : jugador.getInventario()) {
                                if (inventario instanceof Llave llave) {
                                    llaveNecesaria = llave;
                                    break;
                                }
                            }
                        }
                        // Ejecuta la acción de abrir y muestra el mensaje del record RespuestaAccion.
                        RespuestaAccion respuesta = abrible.abrir(llaveNecesaria);
                        System.out.println(respuesta.mensaje());

                        // Si se abrió un contenedor y tiene algo dentro, intenta dárselo al jugador.
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
                    // Mostrar los objetos en el inventario con la misma logica que los objetos de habitaciones
                    System.out.print("Objetos en el inventario: ");
                    boolean hayObjetos = false;
                    boolean hayMasDeUnObjeto = false;
                    for (Objeto objeto : jugador.getInventario()) {
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
                    mostrarObjetosHabitacion();
                    // Lógica del comando EXAMINAR: muestra descripción y, si es Leible, su texto.
                    System.out.print("¿Que objeto quieres examinar? ");
                    String nombreObjeto = scanner.nextLine().toLowerCase(Locale.ROOT);

                    Objeto objeto = buscarObjeto(nombreObjeto);

                    if (objeto == null) {
                        System.out.println("El objeto " + nombreObjeto + " no existe (al menos aqui).");
                    } else {
                        // Siempre mostramos la descripción básica del objeto.
                        System.out.println(objeto.getDescripcion());

                        // Si implementa Leible, mostramos su contenido de texto.
                        if (objeto instanceof Leible leible) {
                            System.out.println("-----------------------------");
                            System.out.println("Lees: " + leible.leer());
                            System.out.println("-----------------------------");
                        }

                        // Si es un contenedor, indicamos si está abierto o cerrado y qué hay dentro.
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

                case "combinar" -> {
                    // Lógica del comando COMBINAR: combina dos objetos Combinable.
                    mostrarObjetosCombinables();
                    // Cuenta cuántos objetos combinables hay entre inventario y habitación.
                    int totalCombinales = 0;
                    for (Objeto obj : jugador.getInventario()) {
                        if (obj instanceof Combinable) totalCombinales++;
                    }
                    for (Objeto obj : objetosMapa[jugador.getHabitacionActual()]) {
                        if (obj != null && obj instanceof Combinable) totalCombinales++;
                    }
                    if (totalCombinales < 2) {
                        System.out.println("No tienes los objetos para combinar");
                        break;
                    }

                    // Pedir primer objeto al jugador.

                    System.out.println("Que objeto quieres combinar primero: ");
                    String nombre1 = scanner.nextLine().toLowerCase(Locale.ROOT);
                    Objeto objeto1 = buscarObjeto(nombre1);

                    if (!(objeto1 instanceof Combinable)) {
                        System.out.println("El " + nombre1 + " no se puede combinar con nada.");
                        break;
                    }

                    // Pedir segundo objeto

                    System.out.println("Que objeto quieres combinar segundo: ");
                    String nombre2 = scanner.nextLine().toLowerCase(Locale.ROOT);
                    Objeto objeto2 = buscarObjeto(nombre2);

                    if (objeto2 == null) {
                        System.out.println("No se encuentra el objeto " + nombre2);
                        break;
                    }

                    if (!(objeto2 instanceof Combinable)) {
                        System.out.println("El " + nombre2 + " no se puede combinar con nada");
                        break;
                    }

                    if (objeto1 == objeto2) {
                        System.out.println("No puedes combinar un objeto con el mismo");
                    }

                    // Se intenta la combinación usando la interfaz Combinable.
                    Combinable comb1 = (Combinable) objeto1;
                    Objeto resultado = comb1.combinar(objeto2);

                    if (resultado == null) {
                        System.out.println("No puedes combinar " + objeto1.getNombre() + " con " + objeto2.getNombre());
                    } else {
                        System.out.println("Has combinado " + objeto1.getNombre() + " con " + objeto2.getNombre());
                    }


                    // Elimina los dos objetos usados (del inventario o de la habitación).
                    consumirObjeto(objeto1);
                    consumirObjeto(objeto2);

                    // Intenta meter el resultado en el inventario; si no cabe, lo deja en la habitación.
                    if (jugador.agregarAlInventario(resultado)) {
                        System.out.println("Has creado: " + resultado.getNombre() + " - " + resultado.getDescripcion());
                    } else {
                        System.out.println("Tu inventario esta lleno. El " + resultado.getNombre() + " cae al suelode la habitacion");
                        // Dejar objeto en la habitacion
                        for (int i = 0; i < objetosMapa[jugador.getHabitacionActual()].length; i++) {
                            if (objetosMapa[jugador.getHabitacionActual()][i] == null) {
                                objetosMapa[jugador.getHabitacionActual()][i] = resultado;
                                break;
                            }
                        }
                    }
                }


                case "salir" -> {
                    // Termina el bucle principal y cierra el juego.
                    jugando = false;
                    System.out.println("Saliendo del juego...");
                }

                default -> {
                    // Cualquier comando desconocido muestra la ayuda.
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
        System.out.println("Combinar: combina dos objetos para crear algo nuevo");
        System.out.println("Salir: termina el juego");
    }

    /**
     * Muestra la información de la habitación actual
     * y, si hay, la lista de objetos que contiene.
     */
    private static void mostrarInfoHabitacion(){
        System.out.println(habitaciones[jugador.getHabitacionActual()].getDescripcion());

        boolean hayObjetos = false;

        for (Objeto objeto : objetosMapa[jugador.getHabitacionActual()]) {
            if (objeto != null) {
                hayObjetos = true;
                break;
            }
        }

        if (!hayObjetos) {
            System.out.println("No hay objetos en esta habitacion");
        }
        else {
            mostrarObjetosHabitacion();
        }

    }

    /**
     * Procesa el comando "coger [objeto]".
     * Busca el objeto en la habitación actual y, si es posible, lo pasa al inventario del jugador.
     */
    private static void procesarComandoCoger(String objetoACoger) {
        boolean objetoEncontrado = false;
        for (int i = 0; i < objetosMapa[jugador.getHabitacionActual()].length; i++) {
            if (objetosMapa[jugador.getHabitacionActual()][i] != null && objetoACoger.equals(objetosMapa[jugador.getHabitacionActual()][i].getNombre())) {
                objetoEncontrado = true;

                // Verificamos si el objeto es inventariable
                if (!(objetosMapa[jugador.getHabitacionActual()][i] instanceof Inventariable)) {
                    System.out.println("No puedes coger " + objetoACoger);
                    break;
                }
                // Intentar añadir al inventario usando la lógica de Jugador.
                if (jugador.agregarAlInventario(objetosMapa[jugador.getHabitacionActual()][i])) {
                    System.out.println("Has codigo " + objetoACoger + ".");
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
     * return true si hay al menos un objeto, false en caso contrario.
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
     * Busca un objeto por nombre, primero en la habitación actual
     * y si no lo encuentra, en el inventario del jugador.
     */
    private static Objeto buscarObjeto(String nombre) {
        // Buscar primero en la habitación actual.
        for (Objeto obj : objetosMapa[jugador.getHabitacionActual()]) {
            if (obj != null && obj.getNombre().equalsIgnoreCase(nombre)) {
                return obj;
            }
        }
        //Si no, que mire en el invetario
        return jugador.buscarEnInventario(nombre);
    }
    /**
     * Busca una llave con un determinado código en el inventario del jugador.
     * (Actualmente no se usa, pero ejemplifica el llavero automático.)
     */
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
    /**
     * Muestra todos los objetos combinables tanto en inventario como en la habitación actual.
     */
    private static void mostrarObjetosCombinables() {
        System.out.println("Objetos para combinar:");

        System.out.println("En tu inventario:");
        boolean hayEnInventario = false;
        for (Objeto obj : jugador.getInventario()) {
            if (obj instanceof Combinable) {
                System.out.println(obj.getNombre());
                hayEnInventario = true;
            }
        }
        if (!hayEnInventario) {
            System.out.println("  (vacío)");
        }

        System.out.println("En la habitacion:");
        boolean hayEnHabitacion = false;
        for (Objeto obj : objetosMapa[jugador.getHabitacionActual()]) {
            if (obj != null && obj instanceof Combinable) {
                System.out.println(obj.getNombre());
                hayEnHabitacion = true;
            }
        }
        if (!hayEnHabitacion) {
            System.out.println("  (nada)");
        }
    }

    /**
     * Elimina un objeto del inventario o de la habitación actual.
     * Se usa cuando un objeto se consume al combinar.
     */
    private static boolean consumirObjeto(Objeto objeto) {
        // Para quitar del inventario
        if (jugador.quitarDelInventario(objeto)) {
            return true;
        }

        // Si no estaba en inventario, intentar quitarlo de la habitación actual.
        for (int i = 0; i < objetosMapa[jugador.getHabitacionActual()].length; i++) {
            if (objetosMapa[jugador.getHabitacionActual()][i] == objeto) {
                objetosMapa[jugador.getHabitacionActual()][i] = null;
                return true;
            }
        }

        return false;
    }

}