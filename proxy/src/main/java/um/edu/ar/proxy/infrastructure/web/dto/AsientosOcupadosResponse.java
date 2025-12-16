package um.edu.ar.proxy.infrastructure.web.dto;

import lombok.Data;
import um.edu.ar.proxy.domain.model.Asiento;

import java.util.List;

@Data
public class AsientosOcupadosResponse {
    private Long eventoId;
    private List<ItemAsientoResponse> asientos;

    @Data
    public static class ItemAsientoResponse {
        private int fila;
        private int columna;
        private String estado;
        private String expira;
    }

    public static AsientosOcupadosResponse fromDomain(Long eventoId, List<Asiento> domainAsientos) {
        AsientosOcupadosResponse resp = new AsientosOcupadosResponse();
        resp.setEventoId(eventoId);
        resp.setAsientos(
                domainAsientos.stream().map(a -> {
                    ItemAsientoResponse r = new ItemAsientoResponse();
                    r.setFila(a.getFila());
                    r.setColumna(a.getColumna());
                    r.setEstado(a.getEstado().name());
                    r.setExpira(a.getExpira() != null ? a.getExpira().toString() : null);
                    return r;
                }).toList()
        );
        return resp;
    }
}
