package domain;

import interfaces.Inventariable;

public class Jugador {
    private Objeto[] inventario;
    private int habitacionActual;
    private final int TAMAÑO_INVENTARIO = 5;

    public Jugador() {
        this.inventario = new Objeto[TAMAÑO_INVENTARIO];
        this.habitacionActual = 0;
    }

    public Jugador(Objeto[] inventario, int habitacionActual) {
        this.inventario = new Objeto[TAMAÑO_INVENTARIO];
        this.habitacionActual = 0; // El jugador empieza en la habitacion inicial
    }

    public int getHabitacionActual() {
        return habitacionActual;
    }

    public void setHabitacionActual(int habitacionActual) {
        this.habitacionActual = habitacionActual;
    }

    public Objeto[] getInventario() {
        return inventario;
    }

    /*
    Metodo para agregar los objetos al inventario
     */

    public boolean agregarAlInventario(Objeto objeto) {
        if (!(objeto instanceof Inventariable)) {
            return false;
        }

        for (int i = 0; i < inventario.length; i++) {
            if (inventario[i] == null) {
                inventario[i] = objeto;
                return true;
            }
        }
        return false;
    }

    /*
    Metodo para eliminar objetos al inventario
     */

    public boolean quitarDelInventario(Objeto objeto) {
        for (int i = 0; i < inventario.length; i++) {
            if (inventario[i] == objeto) {
                inventario[i] = null;
                return true;
            }
        }
        return false;
    }

    /*
    Metodo para buscar un objeto en el inventario
     */

    public Objeto buscarEnInventario(String nombre) {
        for (Objeto objeto : inventario) {
            if (objeto != null && objeto.getNombre().equalsIgnoreCase(nombre)) {
                return objeto;
            }
        }
        return null;
    }

    public boolean tieneEspacio() {
        for (Objeto objeto : inventario) {
            if (objeto == null) {
                return true;
            }
        }
        return false;
    }

}
