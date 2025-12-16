package um.edu.ar.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoEvento {
    private Long id;
    private String nombre;
    private String descripcion;
}
