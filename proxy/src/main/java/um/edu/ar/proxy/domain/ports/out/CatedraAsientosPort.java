package um.edu.ar.proxy.domain.ports.out;

import um.edu.ar.proxy.domain.model.BloquearAsientosCommand;
import um.edu.ar.proxy.domain.model.BloqueoResultado;

public interface CatedraAsientosPort {
    BloqueoResultado bloquearAsientos(BloquearAsientosCommand command);
}
