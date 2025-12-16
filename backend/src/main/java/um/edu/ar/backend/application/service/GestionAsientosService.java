package um.edu.ar.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import um.edu.ar.backend.domain.model.Asiento;
import um.edu.ar.backend.domain.model.EstadoAsiento;
import um.edu.ar.backend.domain.ports.in.GestionAsientosUseCase;
import um.edu.ar.backend.domain.ports.out.AsientosProxyPort;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GestionAsientosService implements GestionAsientosUseCase {

    private final AsientosProxyPort asientosProxyPort;

    @Override
    public List<Asiento> obtenerAsientosEvento(Long eventoId, int filas, int columnas) {
        List<Asiento> ocupados = asientosProxyPort.obtenerAsientos(eventoId, filas, columnas);

        Map<String, Asiento> ocupadosMap = ocupados.stream()
                .collect(Collectors.toMap(
                        a -> a.getFila() + "-" + a.getColumna(),
                        a -> a,
                        (a1, a2) -> a1
                ));
        List<Asiento> resultado = new ArrayList<>();

        for (int fila = 1; fila <= filas; fila++) {
            for (int columna = 1; columna <= columnas; columna++) {
                String key = fila + "-" + columna;
                Asiento asientoOcupado = ocupadosMap.get(key);

                if (asientoOcupado != null) {
                    resultado.add(asientoOcupado);
                } else {
                    Asiento asientoLibre = new Asiento(
                            fila,
                            columna,
                            EstadoAsiento.LIBRE
                    );
                    resultado.add(asientoLibre);
                }
            }
        }
        return resultado;
    }
}

