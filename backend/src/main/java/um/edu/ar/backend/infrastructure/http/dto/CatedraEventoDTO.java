package um.edu.ar.backend.infrastructure.http.dto;


import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class CatedraEventoDTO {

    private Long id;
    private String titulo;
    private String resumen;
    private String descripcion;
    private String fecha;
    private String direccion;
    private String imagen;
    private Integer filaAsientos;
    private Integer columnAsientos;
    private Double precioEntrada;

    private EventoTipoDTO eventoTipo;
    private List<IntegranteDTO> integrantes;

    @Data
    public static class EventoTipoDTO {
        private String nombre;
        private String descripcion;
    }

    @Data
    public static class IntegranteDTO {
        private String nombre;
        private String apellido;
        private String identificacion;
    }
}
