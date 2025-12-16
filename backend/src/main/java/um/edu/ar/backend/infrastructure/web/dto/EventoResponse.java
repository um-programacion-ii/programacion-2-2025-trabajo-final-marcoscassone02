package um.edu.ar.backend.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoResponse {
    private Long id;
    private String nombre;
    private String resumen;
    private String descripcion;
    private LocalDateTime fechaHora;
    private String direccion;
    private String tipoNombre;
    private Integer filas;
    private Integer columnas;
    private Double precio;
}
