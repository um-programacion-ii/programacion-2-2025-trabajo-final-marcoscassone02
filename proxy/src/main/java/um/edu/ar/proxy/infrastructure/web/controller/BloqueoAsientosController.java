package um.edu.ar.proxy.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.edu.ar.proxy.domain.ports.in.BloquearAsientosPort;
import um.edu.ar.proxy.infrastructure.web.dto.BloquearAsientosRequest;
import um.edu.ar.proxy.infrastructure.web.dto.BloqueoAsientosResponse;

@RestController
@RequestMapping("/proxy")
@RequiredArgsConstructor
public class BloqueoAsientosController {

    private final BloquearAsientosPort bloquearAsientosPort;

    @PostMapping("/bloquear-asientos")
    public ResponseEntity<BloqueoAsientosResponse> bloquear(
            @RequestBody BloquearAsientosRequest request
    ) {
        var command = request.toCommand();
        var resultado = bloquearAsientosPort.bloquearAsientos(command);
        return ResponseEntity.ok(BloqueoAsientosResponse.fromDomain(resultado));
    }
}


