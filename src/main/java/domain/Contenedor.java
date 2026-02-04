package domain;

import interfaces.Abrible;

public class Contenedor extends Mueble implements Abrible {
    private String codigoNecesario;
    private boolean abierto;
    private Objeto objetoGuardado;

    public Contenedor(String nombre, String descripcion, String codigoNecesario, Objeto objetoGuardado) {
        super(nombre, descripcion);
        this.codigoNecesario = codigoNecesario;
        this.abierto = false;
        this.objetoGuardado = objetoGuardado;
    }

    public Contenedor(String nombre, String descripcion, String codigoNecesario) {
        this(nombre, descripcion, codigoNecesario, null);
    }

    @Override
    public RespuestaAccion abrir(Llave llave) {
        // Si ya esta abierto
        if (abierto) {
            return new RespuestaAccion(false, "El " + getNombre() + " ya está abierto.");
        }

        // Si el contenedor no nesecita una llave
        if (codigoNecesario == null) {
            abierto = true;
            if (objetoGuardado != null) {
                return new RespuestaAccion(true,
                        "Has abierto el " + getNombre() + ". Hay un " + objetoGuardado.getNombre() + " dentro");
            }
            return new RespuestaAccion(true, "Has abierto el " + getNombre() + ". Esta vacio.");
        }

        // Si la llave es incorrecta
        if (llave == null) {
            return new RespuestaAccion(false,
                    "El " + getNombre() + " está cerrado con llave. Necesitas la llave correcta.");
        }

        // Si la llave es correcta
        abierto = true;
        if (objetoGuardado != null) {
            return new RespuestaAccion(true,
                    "Has abierto el " + getNombre() + " con la llave. Hay un " + objetoGuardado.getNombre() + " dentro");
        }
        return new RespuestaAccion(true, "Has abierto el " + getNombre() + " con la llave. Está vacío.");
    }

    @Override
    public boolean estaAbierto() {
        return abierto;
    }

    // Sacar objetos del contenedor
    public Objeto sacarObjeto() {
        if (!abierto) {
            return null;
        }

        Objeto obj = objetoGuardado;
        objetoGuardado = null;
        return obj;
    }

    public Objeto getObjetoGuardado() {
        return objetoGuardado;
    }
}

