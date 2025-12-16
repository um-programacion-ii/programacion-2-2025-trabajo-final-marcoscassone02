package um.edu.ar.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaAsiento {
    private int fila;
    private int columna;
    private String persona;
    private String estado;
}
