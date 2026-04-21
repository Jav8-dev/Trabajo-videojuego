package domain;

import exceptions.ObjetoNoCompatibleException;
import interfaces.Combinable;

public class Palo extends Item implements Combinable {
    public static final String NOMBRE = "Palo";
    private static final String DESCRIPCION = "Un palo resistente. Se podría usar para crear algo.";
    private static final boolean VISIBLE = true;
    /**
     * Constructor de la clase Palo.
     */
    public Palo() {
        super(NOMBRE, DESCRIPCION, VISIBLE);
    }

    @Override
    public Objeto combinar(Objeto otro) throws ObjetoNoCompatibleException {
        if (!(otro instanceof Combinable)) {
            throw new ObjetoNoCompatibleException("No se puede combinar " + this.getNombre() + " con " + otro.getNombre());
        } else {
            if (otro.getNombre().equalsIgnoreCase(Cuchilla.NOMBRE)) {
                return new Navaja("Navaja", "Una navaja para cortar cuerdas", true);
            } else {
                throw new ObjetoNoCompatibleException("No se puede combinar " + this.getNombre() + " con " + otro.getNombre());
            }
        }
    }
}
