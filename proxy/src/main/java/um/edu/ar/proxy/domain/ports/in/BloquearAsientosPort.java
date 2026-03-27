package um.edu.ar.proxy.domain.ports.in;

import um.edu.ar.proxy.domain.model.BloquearAsientosCommand;
import um.edu.ar.proxy.domain.model.BloqueoResultado;

public interface BloquearAsientosPort {
    BloqueoResultado bloquearAsientos(BloquearAsientosCommand command);
}
