package um.edu.ar.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSession {

    private String sessionId;
    private List<Seat> asientosSeleccionados;
    private LocalDateTime fechaExpiracion;

}
