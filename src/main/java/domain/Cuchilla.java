package domain;

import exceptions.ObjetoNoCompatibleException;
import interfaces.Combinable;

public class Cuchilla extends Item implements Combinable {

    public static final String NOMBRE = "Cuchilla";
    private static final String DESCRIPCION = "Una cuchilla muy afilada.";
    private static final boolean VISIBLE = true;

    /**
     * Constructor de la clase MangoRotoLlave.
     */
    public Cuchilla() {
        super(NOMBRE, DESCRIPCION, VISIBLE);
    }

    @Override
    public Objeto combinar(Objeto otro) throws ObjetoNoCompatibleException {
        if (!(otro instanceof Combinable combinable)) {
            throw new ObjetoNoCompatibleException("No se puede combinar " + this.getNombre() + " con " + otro.getNombre());
        } else {
            if (otro.getNombre().equalsIgnoreCase(Palo.NOMBRE)) {
                // Delegar la combinación al otro objeto para evitar duplicación de código
                return combinable.combinar(this);
            } else {
                throw new ObjetoNoCompatibleException("No se puede combinar " + this.getNombre() + " con " + otro.getNombre());
            }
        }
    }

}
