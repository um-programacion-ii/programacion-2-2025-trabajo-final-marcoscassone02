package um.edu.ar.backend.infrastructure.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.edu.ar.backend.domain.model.Venta;
import um.edu.ar.backend.domain.ports.out.VentaRepositoryPort;
import um.edu.ar.backend.infrastructure.persistence.entity.VentaEntity;
import um.edu.ar.backend.infrastructure.persistence.mapper.VentaPersistenceMapper;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VentaJpaRepositoryAdapter implements VentaRepositoryPort {

    private final VentaJpaRepository jpaRepository;
    private final VentaPersistenceMapper mapper;

    @Override
    public Venta save(Venta venta) {
        var entity = mapper.toEntity(venta);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public List<Venta> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Venta> findByVentaIdCatedra(Long ventaIdCatedra) {
        return jpaRepository.findByVentaIdCatedra(ventaIdCatedra)
                .map(mapper::toDomain);
    }


}
