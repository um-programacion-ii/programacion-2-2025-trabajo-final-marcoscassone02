package um.edu.ar.backend.domain.ports.out;

import um.edu.ar.backend.domain.model.Venta;

import java.util.List;
import java.util.Optional;

public interface VentaRepositoryPort {

    Venta save(Venta venta);

    Optional<Venta> findById(Long id);

    List<Venta> findAll();

    List<Venta> findByResultado(boolean resultado);
}
