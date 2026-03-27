package um.edu.ar.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionState implements Serializable {

    private String sessionId;
    private String username;
    private Instant lastActivity;
    private Step pasoActual;

    private Long eventoId;
    private Long ventaId;

    private List<AsientoSeleccionado> asientos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AsientoSeleccionado implements Serializable {
        private int fila;
        private int columna;
        private String persona;
    }

    public enum Step {
        LISTA_EVENTOS,
        DETALLE_EVENTO,
        SELECCION_ASIENTOS,
        CARGA_NOMBRES,
        VENTA_DETALLES,
        VENTA_COMPLETADA
    }
}

