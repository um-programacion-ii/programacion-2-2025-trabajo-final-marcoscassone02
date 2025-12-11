package um.edu.ar.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import um.edu.ar.backend.domain.model.Venta;
import um.edu.ar.backend.domain.ports.in.ListarVentasUseCase;
import um.edu.ar.backend.domain.ports.in.ObtenerVentaPorIdUseCase;
import um.edu.ar.backend.domain.ports.out.VentaRepositoryPort;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VentaConsultaService implements ListarVentasUseCase, ObtenerVentaPorIdUseCase {

    private final VentaRepositoryPort ventaRepository;

    @Override
    public List<Venta> listarVentas() {
        var ventas = ventaRepository.findAll();
        System.out.println("DEBUG VENTAS: cantidad = " + ventas.size());
        return ventas;
    }

    @Override
    public Optional<Venta> obtenerVenta(Long ventaIdCatedra) {
        return ventaRepository.findByVentaIdCatedra(ventaIdCatedra);
    }
}
