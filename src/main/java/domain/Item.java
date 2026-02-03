package domain;

import interfaces.Inventariable;

public class Item extends Objeto implements Inventariable {

    public Item(String nombre, String descripcion) {
        super(nombre, descripcion);
    }
}

//@author Jav8-dev|@version 1.0.