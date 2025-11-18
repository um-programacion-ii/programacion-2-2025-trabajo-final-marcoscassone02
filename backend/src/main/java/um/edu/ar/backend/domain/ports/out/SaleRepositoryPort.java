package um.edu.ar.backend.domain.ports.out;

import um.edu.ar.backend.domain.model.Sale;

import java.util.Optional;

public interface SaleRepositoryPort {
    Sale save(Sale sale);
    Optional<Sale> findById(Long id);
}