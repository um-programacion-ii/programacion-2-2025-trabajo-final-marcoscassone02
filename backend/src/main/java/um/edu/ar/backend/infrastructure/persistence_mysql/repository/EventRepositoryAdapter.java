package um.edu.ar.backend.infrastructure.persistence_mysql.repository;

import org.springframework.stereotype.Repository;
import um.edu.ar.backend.domain.model.Event;
import um.edu.ar.backend.domain.ports.out.EventRepositoryPort;
import um.edu.ar.backend.infrastructure.persistence_mysql.entity.EventEntity;
import um.edu.ar.backend.infrastructure.persistence_mysql.mapper.EventMapper;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EventRepositoryAdapter implements EventRepositoryPort {

    private final JpaEventRepository jpaRepository;

    public EventRepositoryAdapter(JpaEventRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Event save(Event event) {
        EventEntity entity = EventMapper.toEntity(event);
        EventEntity savedEntity = jpaRepository.save(entity);
        return EventMapper.toDomain(savedEntity);
    }

    @Override
    public Event findById(Long eventId) {
        return jpaRepository.findById(eventId)
                .map(EventMapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<Event> findAll() {
        return jpaRepository.findAll().stream()
                .map(EventMapper::toDomain)
                .collect(Collectors.toList());
    }
}
