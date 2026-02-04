package domain;

import interfaces.Combinable;

public class Trapo extends Item implements Combinable{
    public Trapo( String nombre, String descripcion){
        super(nombre, descripcion);
    }
    @Override
    public Objeto combinar(Objeto otro) {
        // se lo dejamos al palo
        if (otro instanceof Palo palo) {
            return palo.combinar(this);
        }

        return null;
    }
}
