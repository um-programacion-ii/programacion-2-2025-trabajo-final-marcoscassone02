package um.edu.ar.proxy.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsientoVentaDto {
    private int fila;
    private int columna;
    private String persona;
}