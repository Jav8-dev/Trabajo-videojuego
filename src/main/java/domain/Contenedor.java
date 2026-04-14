package domain;

import interfaces.Abrible;

public class Contenedor extends Objeto implements Abrible {
    private boolean abierto;
    private String codigoNecesario; // Si es null, no necesita llave
    private Objeto contenido; // Lo que hay dentro

    // CONSTRUCTOR 1: Para cosas con llave (Cofres)
    /**
     * Constructor de la clase Contenedor que requiere una llave para abrirse.
     *
     * @param nombre        El nombre del contenedor.
     * @param descripcion   La descripción del contenedor.
     * @param visible       Indica si el contenedor es visible o no.
     * @param codigo        El código necesario para abrir el contenedor.
     * @param contenido     El objeto contenido dentro del contenedor.
     */
    public Contenedor(String nombre, String descripcion, boolean visible, String codigo, Objeto contenido) {
        super(nombre, descripcion, visible);
        this.codigoNecesario = codigo;
        this.contenido = contenido;
        this.abierto = false;
    }

    // CONSTRUCTOR 2: Para cosas sin llave (Cajones, Armarios)
    /**
     * Constructor de la clase Contenedor que no requiere una llave para abrirse.
     *
     * @param nombre        El nombre del contenedor.
     * @param descripcion   La descripción del contenedor.
     * @param visible       Indica si el contenedor es visible o no.
     * @param contenido     El objeto contenido dentro del contenedor.
     */
    public Contenedor(String nombre, String descripcion, boolean visible, Objeto contenido) {
        super(nombre, descripcion, visible);
        this.codigoNecesario = null; // MARCA IMPORTANTE
        this.contenido = contenido;
        this.abierto = false;
    }

    @Override
    public RespuestaAccion abrir(Llave llave) {
        if (abierto) {
            return new RespuestaAccion(false, "Eso ya está abierto, no pierdas el tiempo.");
        }

        // CASO 1: No necesita llave (El cajón)
        // Caso: Se abre con la mano (null)
        if (this.codigoNecesario == null) {
            abierto = true;
            return new RespuestaAccion(true, "Abres " + getNombre() + " sin problemas.");
        }

        // CASO 2: Necesita llave (El cofre)
        if (llave != null) {
            if (llave.getCodigoSeguridad().equals(this.codigoNecesario)) {
                abierto = true;
                return new RespuestaAccion(true, "Clic. La llave gira y abres " + getNombre());
            }
            else {
                return new RespuestaAccion(false, "La llave no encaja.");
            }
        }

        // CASO 3: Fallo
        return new RespuestaAccion(false, "Está cerrado. Necesitas una llave.");
    }

    @Override
    public boolean estaAbierto() {
        return abierto;
    }

    @Override
    public String getCodigoNecesario() {
        return codigoNecesario;
    }

    @Override
    public Objeto getContenido() {
        return contenido;
    }

    @Override
    public void setContenido(Objeto contenido) {
        this.contenido = contenido;
    }

    @Override
    public void cerrar() {
        this.abierto = false;
    }
}
