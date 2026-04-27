package interfaces;

import domain.Objeto;
import exceptions.ObjetoNoCompatibleException;

public interface Combinable {
    /**
     * Método para combinar el objeto actual con otro objeto.
     *
     * @param otro El otro objeto con el que se va a combinar.
     * @return El resultado de la combinación, que puede ser un nuevo objeto
     * @throws ObjetoNoCompatibleException Si los objetos no son compatibles para combinarse.
     */
    Objeto combinar(Objeto otro) throws ObjetoNoCompatibleException;
}

