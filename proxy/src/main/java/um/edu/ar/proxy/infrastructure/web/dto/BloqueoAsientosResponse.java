package um.edu.ar.proxy.infrastructure.web.dto;

import lombok.Data;
import um.edu.ar.proxy.domain.model.BloqueoResultado;

import java.util.List;

@Data
public class BloqueoAsientosResponse {
    private boolean resultado;
    private String descripcion;
    private Long eventoId;
    private List<AsientoEstadoDto> asientos;

    @Data
    public static class AsientoEstadoDto {
        private int fila;
        private int columna;
        private String estado;
    }

    public static BloqueoAsientosResponse fromDomain(BloqueoResultado domain) {
        BloqueoAsientosResponse resp = new BloqueoAsientosResponse();
        resp.setResultado(domain.isResultado());
        resp.setDescripcion(domain.getDescripcion());
        resp.setEventoId(domain.getEventoId());
        resp.setAsientos(
                domain.getAsientos().stream().map(a -> {
                    AsientoEstadoDto dto = new AsientoEstadoDto();
                    dto.setFila(a.getFila());
                    dto.setColumna(a.getColumna());
                    dto.setEstado(a.getEstado().name());
                    return dto;
                }).toList()
        );
        return resp;
    }
}

