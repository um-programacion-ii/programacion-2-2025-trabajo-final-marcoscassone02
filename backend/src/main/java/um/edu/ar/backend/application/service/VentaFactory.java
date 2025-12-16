package um.edu.ar.backend.application.service;

import um.edu.ar.backend.domain.model.Venta;
import um.edu.ar.backend.domain.model.VentaAsiento;
import um.edu.ar.backend.domain.ports.in.RealizarVentaUseCase;
import um.edu.ar.backend.domain.ports.out.CatedraVentaPort;

import java.time.Instant;
import java.util.List;

public class VentaFactory {

    // Bloqueo inválido
    public static Venta fromBloqueoFallido(
            RealizarVentaUseCase.RealizarVentaCommand command,
            List<RealizarVentaUseCase.RealizarVentaCommand.AsientoVenta> asientosNoBloqueados,
            double precioTotal
    ) {
        Venta venta = new Venta();
        venta.setEventoId(command.eventoId());
        venta.setVentaIdCatedra(null);
        venta.setFechaVenta(Instant.now());
        venta.setResultado(false);
        venta.setDescripcion("Bloqueo inválido o expirado");
        venta.setPrecioVenta(precioTotal);

        venta.setAsientos(
                asientosNoBloqueados.stream()
                        .map(a -> {
                            VentaAsiento va = new VentaAsiento();
                            va.setFila(a.fila());
                            va.setColumna(a.columna());
                            va.setPersona(a.persona());
                            va.setEstado("NO BLOQUEADO");
                            return va;
                        })
                        .toList()
        );

        return venta;
    }


    public static Venta fromFallido(
            RealizarVentaUseCase.RealizarVentaCommand command,
            CatedraVentaPort.VentaResultado resultado,
            double precioTotal
    ) {
        Venta venta = new Venta();
        venta.setEventoId(command.eventoId());
        venta.setVentaIdCatedra(resultado.ventaId());
        venta.setFechaVenta(Instant.now());
        venta.setResultado(false);
        venta.setDescripcion(resultado.descripcion());
        venta.setPrecioVenta(precioTotal);

        venta.setAsientos(
                resultado.asientos().stream()
                        .map(a -> {
                            VentaAsiento va = new VentaAsiento();
                            va.setFila(a.fila());
                            va.setColumna(a.columna());
                            va.setPersona(a.persona());
                            va.setEstado(a.estado());
                            return va;
                        })
                        .toList()
        );

        return venta;
    }


    public static Venta fromExitoso(
            RealizarVentaUseCase.RealizarVentaCommand command,
            CatedraVentaPort.VentaResultado resultado,
            double precioTotal
    ) {
        Venta venta = new Venta();
        venta.setEventoId(command.eventoId());
        venta.setVentaIdCatedra(resultado.ventaId());
        venta.setFechaVenta(Instant.now());
        venta.setResultado(true);
        venta.setDescripcion(resultado.descripcion());
        venta.setPrecioVenta(resultado.precioVenta());
        venta.setPrecioVenta(precioTotal);

        venta.setAsientos(
                resultado.asientos().stream()
                        .map(a -> {
                            VentaAsiento va = new VentaAsiento();
                            va.setFila(a.fila());
                            va.setColumna(a.columna());
                            va.setPersona(a.persona());
                            va.setEstado(a.estado());
                            return va;
                        })
                        .toList()
        );

        return venta;
    }
}
