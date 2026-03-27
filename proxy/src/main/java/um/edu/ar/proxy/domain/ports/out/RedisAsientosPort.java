package um.edu.ar.proxy.domain.ports.out;

import um.edu.ar.proxy.domain.model.Asiento;

import java.util.List;

public interface RedisAsientosPort {
    List<Asiento> obtenerAsientosOcupados(Long eventoId);
}
