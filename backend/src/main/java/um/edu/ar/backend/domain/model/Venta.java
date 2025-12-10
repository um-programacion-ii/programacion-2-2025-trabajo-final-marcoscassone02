package um.edu.ar.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    private Long id;
    private Long eventoId;
    private Long ventaIdCatedra;

    private Instant fechaVenta;
    private boolean resultado;
    private String descripcion;
    private double precioVenta;

    private List<VentaAsiento> asientos;
}
