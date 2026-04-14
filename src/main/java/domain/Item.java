package domain;

import interfaces.Inventariable;

public class Item extends Objeto implements Inventariable {
    public Item(String nombre, String descripcion, boolean visible) {
        super(nombre, descripcion, visible);
    }
}

//@author Jav8-dev|@version 1.0.