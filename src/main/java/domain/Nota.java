package domain;

import interfaces.Leible;

public class Nota extends Item implements Leible {
    private final String texto;

    /**
     * Constructor de la clase Nota.
     *
     * @param nombre      El nombre de la nota.
     * @param descripcion La descripción de la nota.
     * @param visible     Indica si la nota es visible o no.
     * @param texto       El contenido textual de la nota.
     */
    public Nota(String nombre, String descripcion, boolean visible, String texto) {
        super(nombre, descripcion, visible);
        this.texto = texto;
    }

    @Override
    public String leer() {
        return texto;
    }
}
