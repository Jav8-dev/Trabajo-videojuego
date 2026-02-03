package domain;

import interfaces.Leible;

public class Nota extends Item implements Leible {
    private String texto;

    public Nota(String nombre, String descripcion, String texto) {
        super(nombre, descripcion);
        this.texto = texto;
    }

    @Override
    public String leer() {
        return texto;
    }
}
