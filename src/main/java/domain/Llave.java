package domain;

public class Llave extends Item {
    private String codigoSeguridad;

    /**
     * Constructor de la clase Llave.
     *
     * @param nombre          El nombre de la llave.
     * @param descripcion     La descripción de la llave.
     * @param visible         Indica si la llave es visible o no.
     * @param codigoSeguridad El código de seguridad asociado a la llave.
     */
    public Llave(String nombre, String descripcion, boolean visible, String codigoSeguridad) {
        super(nombre, descripcion, visible);
        this.codigoSeguridad = codigoSeguridad;
    }

    /**
     * Obtiene el código de seguridad de la llave.
     *
     * @return El código de seguridad.
     */
    public String getCodigoSeguridad() {
        return codigoSeguridad;
    }
}
//@author Jav8-dev|@version 1.0.