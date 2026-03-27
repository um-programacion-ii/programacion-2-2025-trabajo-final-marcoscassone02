package um.edu.ar.backend.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "venta_asientos")
@Data
public class VentaAsientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int fila;
    private int columna;
    private String persona;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    private VentaEntity venta;
}

