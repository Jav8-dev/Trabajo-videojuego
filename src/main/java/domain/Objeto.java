package domain;

public abstract class Objeto extends Entidad {
    private boolean visible;

    /**
     * Constructor de la clase Objeto.
     *
     * @param nombre      Nombre del objeto.
     * @param descripcion Descripción del objeto.
     * @param visible     Indica si el objeto es visible.
     */
    public Objeto(String nombre, String descripcion, boolean visible) {
        super(nombre, descripcion);
        this.visible = visible;
    }

    /**
     * Indica si el objeto es visible.
     *
     * @return true si el objeto es visible, false en caso contrario.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Establece la visibilidad del objeto.
     *
     * @param visible true para hacer el objeto visible, false para ocultarlo.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }


}
//@author Jav8-dev|@version 1.0.