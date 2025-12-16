package um.edu.ar.proxy.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class BloqueoResultado {
    private boolean resultado;
    private String descripcion;
    private Long eventoId;
    private List<Asiento> asientos;
}