package interfaces;

import domain.Llave;
import domain.RespuestaAccion;

public interface Abrible {
    RespuestaAccion abrir(Llave llave);
    boolean estaAbierto();
}
