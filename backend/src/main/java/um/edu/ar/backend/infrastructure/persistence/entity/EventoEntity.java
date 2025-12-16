package um.edu.ar.backend.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;


import java.time.LocalDateTime;

@Entity
@Table(name = "eventos")
@Data
public class EventoEntity {

    @Id
    private Long id;

    private String nombre;
    private String resumen;
    private String descripcion;
    private LocalDateTime fechaHora;
    private String direccion;

    private Long tipoId;
    private String tipoNombre;
    private String tipoDescripcion;

    private Integer filas;
    private Integer columnas;
    private Double precio;
}
