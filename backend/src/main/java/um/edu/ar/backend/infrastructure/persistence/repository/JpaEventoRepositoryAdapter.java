package um.edu.ar.backend.infrastructure.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.edu.ar.backend.domain.model.Evento;
import um.edu.ar.backend.domain.ports.out.EventoRepository;
import um.edu.ar.backend.infrastructure.persistence.entity.EventoEntity;
import um.edu.ar.backend.infrastructure.persistence.mapper.EventoMapper;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaEventoRepositoryAdapter implements EventoRepository {

    private final JpaEventoRepository jpaEventoRepository;
    private final EventoMapper eventoMapper;

    @Override
    public List<Evento> findAll() {
        List<EventoEntity> entities = jpaEventoRepository.findAll();
        return eventoMapper.toDomainList(entities);
    }

    @Override
    public Optional<Evento> findById(Long id) {
        return jpaEventoRepository.findById(id).map(eventoMapper::toDomain);
    }

    @Override
    public Evento save(Evento evento) {
        EventoEntity entity = eventoMapper.toEntity(evento);
        EventoEntity saved = jpaEventoRepository.save(entity);
        return eventoMapper.toDomain(saved);
    }

    @Override
    public void saveAll(List<Evento> eventos) {
        List<EventoEntity> entities = eventoMapper.toEntityList(eventos);
        jpaEventoRepository.saveAll(entities);
    }
}
