package um.edu.ar.backend.infrastructure.http.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProxyAsientosResponse {
    private Long eventoId;
    private List<ProxyAsientoDto> asientos;
}