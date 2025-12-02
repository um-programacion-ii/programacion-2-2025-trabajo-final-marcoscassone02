package um.edu.ar.backend.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "asientos_bloqueados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsientoBloqueado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sessionId;

    private Long eventoId;

    private int fila;

    private int columna;

    private Instant expiracion;
}

