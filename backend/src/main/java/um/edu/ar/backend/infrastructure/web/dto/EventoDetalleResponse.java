package um.edu.ar.backend.infrastructure.web.dto;


import java.time.Instant;
import java.util.List;

public record EventoDetalleResponse(
        String titulo,
        String resumen,
        String descripcion,
        Instant fecha,
        String direccion,
        String imagen,
        int filaAsientos,
        int columnAsientos,
        double precioEntrada,
        EventoTipoDto eventoTipo,
        List<IntegranteDto> integrantes,
        Long id
) {
    public record EventoTipoDto(String nombre, String descripcion) {}
    public record IntegranteDto(String nombre, String apellido, String identificacion) {}
}
