package io;

import domain.Habitacion;

import java.util.Map;

public class AventuraConfig {

    private String descripcion;
    private Map<String, Habitacion> habitaciones;

    /**
     * @param descripcion  Descripción general del juego.
     * @param habitaciones Mapa de habitaciones del mapa
     */

    public AventuraConfig(String descripcion, Map<String, Habitacion> habitaciones) {
        this.descripcion = descripcion;
        this.habitaciones = habitaciones;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Map<String, Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(Map<String, Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }
}
