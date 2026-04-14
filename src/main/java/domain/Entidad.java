package domain;

import java.util.Objects;

public abstract class Entidad {
    private String nombre;
    private String descripcion;

    /**
     * Constructor de la clase Entidad.
     *
     * @param nombre      El nombre de la entidad.
     * @param descripcion La descripción de la entidad.
     */
    public Entidad(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el nombre de la entidad.
     *
     * @return El nombre de la entidad.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la descripción de la entidad.
     *
     * @return La descripción de la entidad.
     */
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Entidad entidad)) return false;

        return nombre.equals(entidad.nombre) && Objects.equals(descripcion, entidad.descripcion);
    }

    @Override
    public int hashCode() {
        int result = nombre.hashCode();
        result = 31 * result + Objects.hashCode(descripcion);
        return result;
    }

    @Override
    public String toString() {
        return nombre;
    }
}

//@author Jav8-dev|@version 1.0.