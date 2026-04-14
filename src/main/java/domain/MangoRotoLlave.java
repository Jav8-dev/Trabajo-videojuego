package domain;

import exceptions.ObjetoNoCompatibleException;
import interfaces.Combinable;

public class MangoRotoLlave extends Item implements Combinable {

    public static final String NOMBRE = "Mango roto de llave";
    private static final String DESCRIPCION = "Un mango metálico roto de una llave";
    private static final boolean VISIBLE = true;

    /**
     * Constructor de la clase MangoRotoLlave.
     */
    public MangoRotoLlave() {
        super(NOMBRE, DESCRIPCION, VISIBLE);
    }

    @Override
    public Objeto combinar(Objeto otro) throws ObjetoNoCompatibleException {
        if (!(otro instanceof Combinable combinable)) {
            throw new ObjetoNoCompatibleException("No se puede combinar " + this.getNombre() + " con " + otro.getNombre());
        } else {
            if (otro.getNombre().equalsIgnoreCase(PaloRotoLlave.NOMBRE)) {
                // Delegar la combinación al otro objeto para evitar duplicación de código
                return combinable.combinar(this);
            } else {
                throw new ObjetoNoCompatibleException("No se puede combinar " + this.getNombre() + " con " + otro.getNombre());
            }
        }
    }

}
