package um.edu.ar.backend.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsientoResponse {
    private int fila;
    private int columna;
    private String estado;
}

