package um.edu.ar.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

    private Long id;
    private Long eventoId;
    private Long clienteId;
    private List<Seat> asientosComprados;
    private BigDecimal precioTotal;
    private LocalDateTime fechaVenta;
    private SaleStatus estado;

}
