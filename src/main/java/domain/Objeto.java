package domain;

public abstract class Objeto extends Entidad{
    protected boolean visible;

    protected Objeto(String nombre, String descripcion) {
      this(nombre, descripcion, true);
    }

    public Objeto(String nombre, String descripcion, boolean visible) {
        super(nombre, descripcion);
        this.visible = visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }
}
//@author Jav8-dev|@version 1.0.