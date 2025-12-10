package um.edu.ar.proxy.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class BloquearAsientosCommand {
    private Long eventoId;
    private List<ParAsiento> asientos;
    public record ParAsiento(int fila, int columna) {}
}
