package um.edu.ar.backend.domain.ports.in;

import um.edu.ar.backend.domain.model.Venta;
import java.util.Optional;

public interface ObtenerVentaPorIdUseCase {
    Optional<Venta> obtenerVenta(Long ventaIdCatedra);
}
