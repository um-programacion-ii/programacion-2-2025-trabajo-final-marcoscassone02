package um.edu.ar.proxy.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaRequest {
    private String fecha;
    private BigDecimal precioVenta;
    private List<AsientoVentaDto> asientos;
}
