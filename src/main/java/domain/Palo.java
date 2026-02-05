package domain;

import interfaces.Combinable;

public class Palo extends Item implements Combinable {

    public Palo(String nombre, String descripcion) {
        super(nombre, descripcion);
    }

    @Override
    public Objeto combinar(Objeto otro) {
        // Comprueba si el otro objeto es un Cuchilla
        if (otro instanceof Cuchilla) {
            return new Navaja("navaja", "Navaja para cortar cuerdas");
        }

        return null;
    }
}
