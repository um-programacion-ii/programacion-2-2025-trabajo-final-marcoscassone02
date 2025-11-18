package um.edu.ar.backend.infrastructure.persistence_mysql.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import um.edu.ar.backend.domain.model.Event;
import um.edu.ar.backend.infrastructure.persistence_mysql.entity.EventEntity;

import java.util.Collections;
import java.util.List;

public final class EventMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static EventEntity toEntity(Event domain) {
        EventEntity entity = new EventEntity();
        entity.setId(domain.getId());
        entity.setTitulo(domain.getTitulo());
        entity.setDescripcion(domain.getDescripcion());
        entity.setFechaHora(domain.getFechaHora());
        entity.setOrganizador(domain.getOrganizador());
        entity.setPresentadoresJson(serializePresentadores(domain.getPresentadores()));
        entity.setTotalAsientos(domain.getTotalAsientos());
        entity.setFilas(domain.getFilas());
        entity.setColumnas(domain.getColumnas());
        return entity;
    }

    public static Event toDomain(EventEntity entity) {

        List<String> presentadoresList = deserializePresentadores(entity.getPresentadoresJson());

        Event domain = new Event(
                entity.getId(),
                entity.getTitulo(),
                entity.getDescripcion(),
                entity.getFechaHora(),
                entity.getOrganizador(),
                presentadoresList,
                entity.getTotalAsientos(),
                entity.getFilas(),
                entity.getColumnas()
        );
        return domain;
    }

    private static String serializePresentadores(List<String> presentadores) {
        if (presentadores == null || presentadores.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(presentadores);
        } catch (JsonProcessingException e) {
            System.err.println("Error al serializar presentadores: " + e.getMessage());
            return null;
        }
    }

    private static List<String> deserializePresentadores(String presentadoresJson) {
        if (presentadoresJson == null || presentadoresJson.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(presentadoresJson, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            System.err.println("Error al deserializar presentadores: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
