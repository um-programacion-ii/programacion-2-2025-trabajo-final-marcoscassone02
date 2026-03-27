package um.edu.ar.proxy.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asiento {
    private int fila;
    private int columna;
    private EstadoAsiento estado;
    private Instant expira;
}
