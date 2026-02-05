package domain;

import interfaces.Combinable;

public class Cuchilla extends Item implements Combinable{
    public Cuchilla(String nombre, String descripcion){
        super(nombre, descripcion);
    }
    @Override
    public Objeto combinar(Objeto otro) {
        // Se lo dejamos al palo
        if (otro instanceof Palo palo) {
            return palo.combinar(this);
        }

        return null;
    }
}
