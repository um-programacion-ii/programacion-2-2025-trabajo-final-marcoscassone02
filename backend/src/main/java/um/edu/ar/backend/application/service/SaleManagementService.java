package um.edu.ar.backend.application.service;

import org.springframework.stereotype.Service;
import um.edu.ar.backend.domain.model.Client;
import um.edu.ar.backend.domain.model.Sale;
import um.edu.ar.backend.domain.model.Seat;
import um.edu.ar.backend.domain.ports.in.ConfirmSaleUseCase;
import um.edu.ar.backend.domain.ports.in.GetSaleDetailsUseCase;
import um.edu.ar.backend.domain.ports.out.CatedraServicePort;
import um.edu.ar.backend.domain.ports.out.SaleRepositoryPort;

import java.util.List;


@Service
public class SaleManagementService implements ConfirmSaleUseCase, GetSaleDetailsUseCase {

    private final CatedraServicePort catedraServicePort;
    private final SaleRepositoryPort saleRepositoryPort;

    public SaleManagementService(CatedraServicePort catedraPort, SaleRepositoryPort saleRepo /*, ... */) {
        this.catedraServicePort = catedraPort;
        this.saleRepositoryPort = saleRepo;
    }

    @Override
    public Sale confirmSale(Long eventId, Long clienteId, List<Seat> seatsToConfirm) {
        return null;
    }

    @Override
    public Sale getSaleDetails(Long saleId) {
        return null;
    }
}
