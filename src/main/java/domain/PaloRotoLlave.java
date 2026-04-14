package domain;

import exceptions.ObjetoNoCompatibleException;
import interfaces.Combinable;

public class PaloRotoLlave extends Item implements Combinable {
    public static final String NOMBRE = "Pequeño palo metálico con aspas";
    private static final String DESCRIPCION = "Un pequeño palo metálico con aspas";
    private static final boolean VISIBLE = true;
    public static final String CODIGO_LLAVE_RESULTANTE = "LLAVEYZ";

    /**
     * Constructor de la clase PaloRotoLlave.
     */
    public PaloRotoLlave() {
        super(NOMBRE, DESCRIPCION, VISIBLE);
    }

    @Override
    public Objeto combinar(Objeto otro) throws ObjetoNoCompatibleException {
        if (!(otro instanceof Combinable)) {
            throw new ObjetoNoCompatibleException("No se puede combinar " + this.getNombre() + " con " + otro.getNombre());
        } else {
            if (otro.getNombre().equalsIgnoreCase(MangoRotoLlave.NOMBRE)) {
                return new Llave("Llave violeta", "Una llave mediana de color violeta", true, CODIGO_LLAVE_RESULTANTE);
            } else {
                throw new ObjetoNoCompatibleException("No se puede combinar " + this.getNombre() + " con " + otro.getNombre());
            }
        }
    }
}
