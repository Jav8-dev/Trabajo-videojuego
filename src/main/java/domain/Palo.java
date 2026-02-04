package domain;

import interfaces.Combinable;

public class Palo extends Item implements Combinable {

    public Palo(String nombre, String descripcion) {
        super(nombre, descripcion);
    }

    @Override
    public Objeto combinar(Objeto otro) {
        // Comprueba si el otro objeto es un Trapo
        if (otro instanceof Trapo) {
            return new Antorcha("antorcha", "Una antorcha que ilumina");
        }

        return null;
    }
}
