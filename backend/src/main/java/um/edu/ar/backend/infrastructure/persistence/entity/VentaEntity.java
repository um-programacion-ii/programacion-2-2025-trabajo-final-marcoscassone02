package um.edu.ar.backend.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "ventas")
@Data
public class VentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eventoId;
    private Long ventaIdCatedra;

    private Instant fechaVenta;
    private boolean resultado;
    private String descripcion;
    private double precioVenta;

    @OneToMany(
            mappedBy = "venta",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<VentaAsientoEntity> asientos;
}