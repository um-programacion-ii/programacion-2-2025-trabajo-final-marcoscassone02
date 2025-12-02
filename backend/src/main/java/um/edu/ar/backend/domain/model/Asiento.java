package um.edu.ar.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asiento {
    private int fila;
    private int columna;
    private EstadoAsiento estado;
}
