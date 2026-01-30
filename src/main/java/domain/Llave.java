package domain;

public class Llave extends Item {
    private final String codigoSeguridad;


    public Llave(String nombre, String descripcion, String codigoSeguridad) {
        super(nombre, descripcion);
        this.codigoSeguridad = codigoSeguridad;
    }

    public String getCodigoSeguridad() {
        return codigoSeguridad;
    }
}
//@author Jav8-dev|@version 1.0.