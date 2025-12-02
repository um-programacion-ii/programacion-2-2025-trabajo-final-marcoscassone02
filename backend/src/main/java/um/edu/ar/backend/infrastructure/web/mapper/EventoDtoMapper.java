package um.edu.ar.backend.infrastructure.web.mapper;

import org.springframework.stereotype.Component;
import um.edu.ar.backend.domain.model.Evento;
import um.edu.ar.backend.infrastructure.web.dto.EventoResponse;

import java.util.List;

@Component
public class EventoDtoMapper {

    public EventoResponse toResponse(Evento e) {
        return new EventoResponse(
                e.getId(),
                e.getNombre(),
                e.getResumen(),
                e.getDescripcion(),
                e.getFechaHora(),
                e.getDireccion(),
                e.getTipo() != null ? e.getTipo().getNombre() : null,
                e.getFilas(),
                e.getColumnas(),
                e.getPrecio()
        );
    }

    public List<EventoResponse> toResponseList(List<Evento> eventos) {
        return eventos.stream().map(this::toResponse).toList();
    }
}
