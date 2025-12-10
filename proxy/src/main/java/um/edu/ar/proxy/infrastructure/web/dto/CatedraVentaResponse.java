package um.edu.ar.proxy.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatedraVentaResponse {

    private Long eventoId;
    private Long ventaId;
    private String fechaVenta;
    private List<AsientoVentaResponseDto> asientos;
    private boolean resultado;
    private String descripcion;
    private BigDecimal precioVenta;
}
