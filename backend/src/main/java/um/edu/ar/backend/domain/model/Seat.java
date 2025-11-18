package um.edu.ar.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    private Long id;
    private Long eventoId;
    private int fila;
    private int columna;
    private SeatStatus estado;
    private BigDecimal precio;

}
