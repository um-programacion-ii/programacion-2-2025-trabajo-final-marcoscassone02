package um.edu.ar.backend.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import um.edu.ar.backend.domain.model.Venta;
import um.edu.ar.backend.domain.model.VentaAsiento;
import um.edu.ar.backend.infrastructure.persistence.entity.VentaAsientoEntity;
import um.edu.ar.backend.infrastructure.persistence.entity.VentaEntity;

import java.util.List;

@Component
public class VentaPersistenceMapper {

    public VentaEntity toEntity(Venta venta) {
        if (venta == null) return null;

        VentaEntity entity = new VentaEntity();
        entity.setId(venta.getId());
        entity.setEventoId(venta.getEventoId());
        entity.setVentaIdCatedra(venta.getVentaIdCatedra());
        entity.setFechaVenta(venta.getFechaVenta());
        entity.setResultado(venta.isResultado());
        entity.setDescripcion(venta.getDescripcion());
        entity.setPrecioVenta(venta.getPrecioVenta());

        if (venta.getAsientos() != null) {
            List<VentaAsientoEntity> asientoEntities =
                    venta.getAsientos().stream()
                            .map(a -> {
                                VentaAsientoEntity ae = new VentaAsientoEntity();
                                ae.setFila(a.getFila());
                                ae.setColumna(a.getColumna());
                                ae.setPersona(a.getPersona());
                                ae.setEstado(a.getEstado());
                                ae.setVenta(entity); // ← clave
                                return ae;
                            })
                            .toList();

            entity.setAsientos(asientoEntities);
        }

        return entity;
    }

    public Venta toDomain(VentaEntity entity) {
        if (entity == null) return null;

        Venta venta = new Venta();
        venta.setId(entity.getId());
        venta.setEventoId(entity.getEventoId());
        venta.setVentaIdCatedra(entity.getVentaIdCatedra());
        venta.setFechaVenta(entity.getFechaVenta());
        venta.setResultado(entity.isResultado());
        venta.setDescripcion(entity.getDescripcion());
        venta.setPrecioVenta(entity.getPrecioVenta());

        if (entity.getAsientos() != null) {
            List<VentaAsiento> asientos =
                    entity.getAsientos().stream()
                            .map(a -> {
                                VentaAsiento va = new VentaAsiento();
                                va.setFila(a.getFila());
                                va.setColumna(a.getColumna());
                                va.setPersona(a.getPersona());
                                va.setEstado(a.getEstado());
                                return va;
                            })
                            .toList();

            venta.setAsientos(asientos);
        }

        return venta;
    }
}

