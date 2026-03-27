package um.edu.ar.backend.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import um.edu.ar.backend.domain.model.Evento;
import um.edu.ar.backend.domain.model.TipoEvento;
import um.edu.ar.backend.infrastructure.persistence.entity.EventoEntity;

import java.util.List;

@Component
public class EventoMapper {

    public Evento toDomain(EventoEntity e) {
        if (e == null) return null;

        TipoEvento tipo = new TipoEvento(
                e.getTipoId(),
                e.getTipoNombre(),
                e.getTipoDescripcion()
        );

        return new Evento(
                e.getId(),
                e.getNombre(),
                e.getResumen(),
                e.getDescripcion(),
                e.getFechaHora(),
                e.getDireccion(),
                tipo,
                e.getFilas(),
                e.getColumnas(),
                e.getPrecio()
        );
    }

    public EventoEntity toEntity(Evento e) {
        if (e == null) return null;

        EventoEntity entity = new EventoEntity();
        entity.setId(e.getId());
        entity.setNombre(e.getNombre());
        entity.setResumen(e.getResumen());
        entity.setDescripcion(e.getDescripcion());
        entity.setFechaHora(e.getFechaHora());
        entity.setDireccion(e.getDireccion());

        if (e.getTipo() != null) {
            entity.setTipoId(e.getTipo().getId());
            entity.setTipoNombre(e.getTipo().getNombre());
            entity.setTipoDescripcion(e.getTipo().getDescripcion());
        }

        entity.setFilas(e.getFilas());
        entity.setColumnas(e.getColumnas());
        entity.setPrecio(e.getPrecio());
        return entity;
    }

    public List<Evento> toDomainList(List<EventoEntity> entities) {
        return entities.stream().map(this::toDomain).toList();
    }

    public List<EventoEntity> toEntityList(List<Evento> eventos) {
        return eventos.stream().map(this::toEntity).toList();
    }
}
