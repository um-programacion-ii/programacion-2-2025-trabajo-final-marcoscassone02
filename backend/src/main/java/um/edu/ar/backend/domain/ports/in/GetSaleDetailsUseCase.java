package um.edu.ar.backend.domain.ports.in;

import um.edu.ar.backend.domain.model.Sale;

public interface GetSaleDetailsUseCase {
    Sale getSaleDetails(Long saleId);
}
