package um.edu.ar.backend.infrastructure.web.mapper;

import org.springframework.stereotype.Component;
import um.edu.ar.backend.domain.model.Asiento;
import um.edu.ar.backend.infrastructure.web.dto.AsientoResponse;

import java.util.List;

@Component
public class AsientoDtoMapper {

    public AsientoResponse toResponse(Asiento asiento) {
        return new AsientoResponse(
                asiento.getFila(),
                asiento.getColumna(),
                asiento.getEstado().name()
        );
    }

    public List<AsientoResponse> toResponseList(List<Asiento> asientos) {
        return asientos.stream()
                .map(this::toResponse)
                .toList();
    }
}

