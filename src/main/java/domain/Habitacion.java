package domain;

import exceptions.AventuraException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Habitacion {
    private String id;
    private String descripcion;
    private List<Objeto> objetos;
    private Map<String, String> salidas;

    /**
     * Constructor de la clase Habitacion.
     * @param id Id para la habitacion
     * @param descripcion Descripción de la habitación.
     */

    public Habitacion(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
        this.objetos = new ArrayList<>();
        this.salidas = new HashMap<>();
    }

    /**
     * Agrega un objeto a la habitación.
     * @param obj Objeto a agregar.
     * @throws AventuraException Si no se puede agregar el objeto.
     */
    public void agregarObjeto(Objeto obj) throws AventuraException {
        if (obj == null) {
            throw new AventuraException("No puedes añadir un objeto nulo");
        }
        objetos.add(obj);
    }

    /**
     * Elimina un objeto de la habitación.
     * @param obj Objeto a eliminar.
     */
    public void eliminarObjeto(Objeto obj) {
        objetos.remove(obj);
    }

    /**
     *
     * @param direccion Direccion de salida.
     * @param idDestino Id de la habitacion de destino
     */

    public void agregarSalida(String direccion, String idDestino) {
        salidas.put(direccion.toLowerCase(), idDestino);
    }

    /**
     *
     * @param direccion Direccion a consultar
     * @return El Id de destino, o null si no existe.
     */

    public String getSalida(String direccion) {
        return salidas.get(direccion.toLowerCase());
    }

    /**
     * Muestra la descripción de la habitación y los objetos presentes en ella.
     * @return Descripción de la habitación con objetos y salidas
     */
    public String mirar() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.descripcion).append("\n");
        if (objetos.isEmpty()) {
            sb.append("Objetos:\n");
            for (Objeto objeto : objetos) {
                sb.append(" - ").append(objeto.getNombre()).append("\n");
            }
        }

        if (!salidas.isEmpty()) {
            sb.append("Salidas: ").append(salidas.keySet()).append("\n");
        }

        return sb.toString();
    }

    /**
     * Obtiene los objetos presentes en la habitación.
     * @return Array de objetos en la habitación.
     */
    public List<Objeto> getObjetos() {
        return objetos;
    }

    /**
     * Obtiene el mapa de salidas de la habitación.
     * @return Mapa de salidas.
     */

    public Map<String, String> getSalidas() {
        return salidas;
    }

    /**
     * Busca un objeto por su nombre en la habitación.
     * @param nombre Nombre del objeto a buscar.
     * @return El objeto si se encuentra, null en caso contrario.
     */
    public Objeto buscar(String nombre) {
        for (Objeto obj : objetos) {
            if (obj != null && obj.getNombre().equalsIgnoreCase(nombre)) {
                return obj;
            }
        }
        return null;
    }
}

