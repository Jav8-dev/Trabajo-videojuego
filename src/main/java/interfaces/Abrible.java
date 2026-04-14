package interfaces;

import domain.Llave;
import domain.Objeto;
import domain.RespuestaAccion;

public interface Abrible {
    /**
     * Intenta abrir el objeto con la llave proporcionada.
     *
     * @param llave La llave utilizada para intentar abrir el objeto.
     * @return RespuestaAccion indicando si la apertura fue exitosa o no.
     */
    RespuestaAccion abrir(Llave llave);

    /**
     * Verifica si el objeto está abierto.
     *
     * @return true si el objeto está abierto, false en caso contrario.
     */
    boolean estaAbierto();

    /**
     * Obtiene el código necesario para abrir el objeto.
     *
     * @return El código necesario para abrir el objeto.
     */
    String getCodigoNecesario();

    /**
     * Obtiene el contenido del objeto.
     *
     * @return El objeto contenido dentro del objeto abrible o null si no hay contenido.
     */
    Objeto getContenido();

    /**
     * Establece el contenido del objeto.
     *
     * @param contenido El objeto que se va a establecer como contenido.
     */
    void setContenido(Objeto contenido);

    /**
     * Cierra el objeto.
     */
    void cerrar();
}