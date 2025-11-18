package um.edu.ar.backend.infrastructure.persistence_mysql.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@AllArgsConstructor
public class EventEntity {

    @Getter
    @Id
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 2000)
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime fechaHora;


    private String organizador;
    private int totalAsientos;
    private int filas;
    private int columnas;

    @Column(columnDefinition = "TEXT")
    private String presentadoresJson;


    private Long asientosVendidos;
    private Long asientosBloqueados;


    public EventEntity() {

    }

}