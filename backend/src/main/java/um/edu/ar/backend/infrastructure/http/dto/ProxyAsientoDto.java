package um.edu.ar.backend.infrastructure.http.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProxyAsientoDto {
    private int fila;
    private int columna;
    private String estado;
    private String expira;

}
