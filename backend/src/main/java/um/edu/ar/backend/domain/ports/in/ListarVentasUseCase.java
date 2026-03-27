package um.edu.ar.backend.domain.ports.in;

import um.edu.ar.backend.domain.model.Venta;
import java.util.List;

public interface ListarVentasUseCase {
    List<Venta> listarVentas();
}
