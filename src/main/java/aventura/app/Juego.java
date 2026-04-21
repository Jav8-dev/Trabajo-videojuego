package aventura.app;

import domain.*;
import exceptions.AventuraException;
import exceptions.InventarioLlenoException;
import exceptions.ObjetoNoCompatibleException;
import interfaces.Abrible;
import interfaces.Combinable;
import interfaces.Inventariable;
import interfaces.Leible;
import io.AventuraConfig;
import io.CargadorAventura;
import io.MiEntradaSalida;
import io.Migrador;

import java.io.IOException;
import java.util.*;

/**
 * Clase principal del juego "Tu Propia Aventura".
 * Esqueleto para la Misión 1 (UD1-UD3).
 * VUESTRO TRABAJO es rellenar todos los TODO
 */
/**
 * Clase principal del juego "Tu Propia Aventura".
 * Esqueleto para la Misión 1 (UD4-UD5).
 */
public class Juego {

    // --- NÚCLEO: Definición de Datos (FASE 1) ---
    // Esta parte os la damos HECHA. Es el "contrato" del núcleo.

    private String descripcionJuego;

    // El mapa de habitaciones.
    private Map<String, Habitacion> habitaciones;

    // El inventario ahora se ha movido a la clase Jugador

    // Variable que guarda la posición actual del jugador
    private Jugador jugador;

    // --- FIN DE LA DEFINICIÓN DE DATOS ---

    /**
     * Constructor de la clase Juego.
     *
     * @param jugador El jugador que participa en el juego.
     */
    public Juego(Jugador jugador) {
        // Inicialización del mapa de habitaciones
        habitaciones = new HashMap<>(); // Cambia el tamaño según el número de habitaciones que tengas
        this.jugador = jugador;
        inicializarJuego();
    }

    /**
     * Inicializa el juego creando las habitaciones y los objetos.
     */
    private void inicializarJuego() {
        try {
            CargadorAventura cargador = new CargadorAventura();
            cargador.cargarConfiguracion();
            AventuraConfig aventuraMundo = cargador.cargarMundoBase();
            this.descripcionJuego = aventuraMundo.getDescripcion();
            this.habitaciones = aventuraMundo.getHabitaciones();
            jugador.setHabitacionActual("inicio");
        } catch (IOException e) {
            System.err.println("Error crítico al cargar el mundo: " + e.getMessage());
        }
    }

    public Map<String, Habitacion> getHabitaciones() {
        return habitaciones;
    }


    public static void main(String[] args) {
        Juego juego = new Juego(new Jugador("Jugador1"));
        //Migrador.migrar(juego.getHabitaciones());
        juego.iniciar();
        System.out.println("¡Gracias por jugar!");


    }

    public void iniciar() {
        // Aquí puedes implementar la lógica para iniciar el juego si es necesario
        boolean jugando = true;

        System.out.println("¡Bienvenido a 'UNDER PIOJOS'!");
        System.out.println("------------------------------------------");
        mostrarAyuda();
        System.out.println("------------------------------------------");

        //Muestra la descripción general del juego
        System.out.println(descripcionJuego);

        //Muestra la descripción de la primera habitación
        System.out.println(getHabitacionActual().mirar());

        while (jugando) {

            //Leer el comando del usuario por teclado
            System.out.print("\n> ");
            String comando = MiEntradaSalida.solicitarCadena("¿Qué quieres hacer? ").toLowerCase(Locale.ROOT);


            switch (comando) {
                case "mirar" -> mostrarInfoHabitacion();
                case "inventario" -> mostrarObjetosInventario();
                case String c when c.startsWith("ir ") -> cmdIr(c.substring(3).trim());
                case "coger" -> cmdCoger();
                case "examinar" -> cmdExaminar();
                case "abrir" -> cmdAbrir();
                case "combinar" -> cmdCombinar();
                case "salir" -> {
                    jugando = false;
                    System.out.println("Saliendo del juego...");
                }

                default -> mostrarAyuda();
            }

        }
    }

    /**
     * Mueve al jugador a la habitación de la izquierda si es posible.
     */
    private void cmdIr(String direccion) {
        String destino = getHabitacionActual().getSalida(direccion.toLowerCase());
        if (destino != null) {
            jugador.setHabitacionActual(destino);
            System.out.println("Te has movido hacia " + direccion);
            mostrarInfoHabitacion();
        } else {
            System.out.println("No puedes ir hacia " + direccion);
        }
    }

    /**
     * Procesa el comando de coger un objeto de la habitación actual.
     */
    private void cmdCoger() {
        if (!hayObjetosEnHabitacion()) {
            System.out.println("No hay objetos para coger en esta habitación.");
            return;
        }

        mostrarObjetosHabitacion();
        System.out.print("¿Qué objeto quieres coger? ");
        String objetoACoger = MiEntradaSalida.solicitarCadena("").trim();

        Objeto objeto = buscarObjeto(objetoACoger);
        if (objeto == null) {
            System.out.println("No se encontró ningún objeto llamado " + objetoACoger + ".");
        }
        else {
            procesarComandoCoger(objeto);
        }
    }

    /**
     * Procesa el comando de examinar un objeto.
     */
    private void cmdExaminar() {
        System.out.println("¿Qué objeto quieres examinar?");
        mostrarTodosLosObjetos();
        String objetoAExaminar = MiEntradaSalida.solicitarCadena("").trim();

        Objeto objeto = buscarObjeto(objetoAExaminar);
        if (objeto == null) {
            System.out.println("No se encontró ningún objeto llamado " + objetoAExaminar + ".");
        } else {
            System.out.println(objeto.getDescripcion());
            if (objeto instanceof Leible leible) {
                System.out.println("Lees: \n" + leible.leer());
            }
        }
    }

    /**
     * Procesa el comando de abrir un contenedor.
     */
    private void cmdAbrir() {
        System.out.println("¿Qué contenedor quieres abrir?");
        //TODO: Hacer que el método siguiente en lugar de mostrar devuelva una lista
        mostrarObjetosAbribles();
        String contenedorAAbrir = MiEntradaSalida.solicitarCadena("").trim();

        Objeto objeto = buscarObjeto(contenedorAAbrir);

        if (objeto == null) {
            System.out.println("No se encontró ningún contenedor llamado " + contenedorAAbrir + ".");
        } else {
            procesarComandoAbrir(objeto);
        }
    }

    /**
     * Procesa el comando de combinar objetos.
     */
    private void cmdCombinar() {
        System.out.println("¿Qué objetos quieres combinar?");
        mostrarTodosLosObjetos();

        String objeto1Nombre = MiEntradaSalida.solicitarCadena("Primer objeto: ").trim();
        Objeto objeto1 = buscarObjeto(objeto1Nombre);

        if (objeto1 == null){
            System.out.printf("No se encontró %s%n", objeto1Nombre);
            return;
        }

        String objeto2Nombre = MiEntradaSalida.solicitarCadena("Segundo objeto: ").trim();
        Objeto objeto2 = buscarObjeto(objeto2Nombre);

        if (objeto2 == null) {
            System.out.printf("No se encontró %s%n", objeto2Nombre);
            return;
        }

        if (objeto1 instanceof Combinable combinable1) {
            try {
                Objeto resultado = combinable1.combinar(objeto2);
                if (resultado != null) {
                    System.out.printf("Has combinado %s y %s para crear %s.%n",
                            objeto1.getNombre(), objeto2.getNombre(), resultado.getNombre());

                    // 1. Eliminar los objetos originales del inventario o de la habitación
                    consumirObjeto(objeto1);
                    consumirObjeto(objeto2);

                    // 2. Añadir el nuevo objeto al inventario
                    try {
                        jugador.coger(resultado);
                        System.out.println("El nuevo objeto está en tu inventario.");
                    } catch (InventarioLlenoException e) {
                        // El inventario está lleno, dejamos el objeto en la habitación
                        System.out.println("¡Cuidado! Tu inventario estaba lleno y el objeto cayó al suelo.");
                        try {
                            getHabitacionActual().agregarObjeto(resultado);
                            System.out.println("El nuevo objeto está en la habitación actual.");
                        } catch (AventuraException ex) {
                            System.out.println("La habitación también está llena... el objeto se ha perdido en el limbo (Bug).");
                        }
                    }
                } else {
                    System.out.println("La combinación no produjo ningún objeto.");
                }

            } catch (ObjetoNoCompatibleException e) {
                System.out.println(e.getMessage());
                return;
            } catch (AventuraException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println(objeto1.getNombre() + " no se puede combinar con otros objetos.");
        }
    }

    /**
     * Muestra la ayuda con los comandos disponibles.
     */
    private void mostrarAyuda() {
        System.out.println("Estos son los comandos que puedes ejecutar:");
        System.out.println("Ir derecha: intenta ir hacia la derecha");
        System.out.println("Ir izquierda: intenta ir hacia la izquierda");
        System.out.println("Mirar: muestra la descripción de la habitación actual y los objetos que hay en ella");
        System.out.println("Examinar: muestra la descripción de un objeto específico");
        System.out.println("Inventario: muestra los objetos que llevas contigo");
        System.out.println("Coger: intenta coger un objeto de la habitación actual");
        System.out.println("Abrir: intenta abrir un contenedor (cajón, cofre, taquilla, etc.)");
        System.out.println("Combinar: intenta combinar dos objetos para crear uno nuevo");
        System.out.println("Salir: termina el juego");
        System.out.println("Escribe sólo el comando, sin parámetros adicionales.");
    }

    /**
     * Muestra la información de la habitación actual.
     */
    private void mostrarInfoHabitacion() {
        System.out.println(getHabitacionActual().mirar());

    }

    /**
     * Procesa el comando de coger un objeto de la habitación actual.
     * @param objetoACoger El objeto que el jugador desea coger.
     */
    private void procesarComandoCoger(Objeto objetoACoger) {
        assert objetoACoger != null : "El objeto a coger no puede ser null";

        if (!getHabitacionActual().getObjetos().contains(objetoACoger)) {
            System.out.println("No hay ningun objeto llamado " + objetoACoger.getNombre() + " en esta habitacion");
            return;
        }

        try {
            jugador.coger(objetoACoger);
            getHabitacionActual().eliminarObjeto(objetoACoger);
            System.out.println("Has cogido " + objetoACoger.getNombre() + " y lo ha añadido a tu inventario.");
        } catch (AventuraException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Procesa el comando de abrir un contenedor.
     * @param objeto El objeto que el jugador desea abrir.
     */
    private void procesarComandoAbrir(Objeto objeto) {
        if (!(objeto instanceof Abrible abrible)) {
            System.out.printf("%s no se puede abrir.%n", objeto.getNombre());
        } else {
            if (abrible.estaAbierto()) {
                System.out.println("Eso ya está abierto, no pierdas el tiempo.");
                return;
            }

            Llave llaveParaUsar = null;

            // Buscar una llave en el inventario que pueda abrir el contenedor
            for (Objeto objInventario : jugador.getInventario()) {
                if (objInventario instanceof Llave llave) {
                    if (abrible.getCodigoNecesario() != null && llave.getCodigoSeguridad().equals(abrible.getCodigoNecesario())) {
                        llaveParaUsar = llave;
                        break;
                    }
                }
            }

            RespuestaAccion respuesta = abrible.abrir(llaveParaUsar);
            System.out.println(respuesta.mensaje());

            if (respuesta.esExito()) {
                if (abrible.getContenido() == null) {
                    System.out.println("El contenedor está vacío.");
                }
                else {
                    System.out.println("Has encontrado: " + abrible.getContenido().getNombre());
                    try {
                        //TODO: Si hubiera más de un objeto dentro, habría que implementar un bucle aquí o hacer que el contenido sea una lista de objetos.
                        jugador.coger(abrible.getContenido());
                        System.out.println("Has cogido " + abrible.getContenido().getNombre() + " y lo has añadido a tu inventario.");
                        abrible.setContenido(null); // Vaciar el contenido del contenedor
                    } catch (InventarioLlenoException e) {
                        System.out.println(e.getMessage());
                                    /*
                                    No ha podido coger el objeto. Para que no haya problemas de pérdida de objetos, lo
                                    que haremos será cerrar el contenedor de nuevo y dejar el objeto dentro.
                                     */
                        abrible.cerrar();
                    } catch (AventuraException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Muestra los objetos presentes en la habitación actual.
     */
    private void mostrarObjetosHabitacion() {
        System.out.print("Objetos en la habitación: ");
        List<Objeto> objetos = getHabitacionActual().getObjetos();
        List<String> nombres = new ArrayList<>();

        for (Objeto objeto : objetos) {
            if (objeto != null && objeto.isVisible()) {
                nombres.add(objeto.getNombre());
            }
        }

        System.out.println(nombres.isEmpty() ? "No hay objetos" : String.join(", ", nombres));
    }

    /**
     * Verifica si hay objetos en la habitación actual.
     *
     * @return true si hay al menos un objeto, false si no hay ninguno.
     */
    private boolean hayObjetosEnHabitacion() {
        return !getHabitacionActual().getObjetos().isEmpty();
    }

    /**
     * Muestra los objetos presentes en el inventario del jugador.
     */
    private void mostrarObjetosInventario() {
        System.out.print("Objetos en el inventario: ");
        List<String> nombres = new ArrayList<>();

        for (Objeto objeto : jugador.getInventario()) {
            if (objeto != null) {
                nombres.add(objeto.getNombre());
            }
        }
        System.out.println(nombres.isEmpty() ? "No hay objetos." : String.join(", ", nombres));
    }

    /**
     * Muestra todos los objetos disponibles, tanto en la habitación actual como en el inventario del jugador.
     */
    private void mostrarTodosLosObjetos() {
        mostrarObjetosHabitacion();
        mostrarObjetosInventario();
    }

    /**
     * Muestra los objetos abribles disponibles, tanto en la habitación actual como en el inventario del jugador.
     */
    private void mostrarObjetosAbribles() {
        System.out.print("Contenedores disponibles: ");
        List<String> nombres = new ArrayList<>();
        for (Objeto objeto : getHabitacionActual().getObjetos()) {
            if (objeto instanceof Abrible) {
                nombres.add(objeto.getNombre());
            }
        }
        for (Objeto objeto : jugador.getInventario()) {
            if (objeto instanceof Abrible) {
                nombres.add(objeto.getNombre());
            }
        }
        System.out.println(nombres.isEmpty() ? "No hay objetos en el inventario" : String.join(", ", nombres));
    }

    /**
     * Busca un objeto por su nombre, primero en la habitación actual y luego en el inventario del jugador.
     *
     * @param nombre El nombre del objeto a buscar.
     * @return El objeto si se encuentra, o null si no se encuentra en ninguno de los dos lugares.
     */
    private Objeto buscarObjeto(String nombre) {
        // 1. Buscamos en la habitación (Prioridad 1: Lo que veo)
        Objeto encontrado = getHabitacionActual().buscar(nombre);

        if (encontrado != null) {
            return encontrado;
        }

        // 2. Si no está en la sala, buscamos en el bolsillo (Prioridad 2: Lo que tengo)
        return jugador.buscarEnInventario(nombre);
    }

    /**
     * Obtiene la habitación actual del jugador.
     * @return La habitación en la que se encuentra el jugador.
     */
    private Habitacion getHabitacionActual() {
        return habitaciones.get(jugador.getHabitacionActual());
    }

    /**
     * Elimina un objeto del juego, ya sea que esté en la habitación o en el inventario.
     * Usado tras combinar objetos.
     */
    private void consumirObjeto(Objeto obj) {
        // Intentamos borrar del inventario
        jugador.eliminarDeInventario(obj);
        // Intentamos borrar de la habitación
        getHabitacionActual().eliminarObjeto(obj);
    }

}