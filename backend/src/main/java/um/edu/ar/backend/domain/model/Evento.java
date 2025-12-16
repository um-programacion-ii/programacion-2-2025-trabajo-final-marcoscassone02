package um.edu.ar.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evento {
    private Long id;
    private String nombre;
    private String resumen;
    private String descripcion;
    private LocalDateTime fechaHora;
    private String direccion;
    private TipoEvento tipo;
    private Integer filas;
    private Integer columnas;
    private Double precio;
}
