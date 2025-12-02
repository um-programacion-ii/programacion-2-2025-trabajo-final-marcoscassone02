package um.edu.ar.backend.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.edu.ar.backend.domain.ports.out.SesionTokenService;
import um.edu.ar.backend.infrastructure.http.dto.AuthRequest;
import um.edu.ar.backend.application.service.CatedraAuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SesionTokenService sesionTokenService;
    private final CatedraAuthService catedraAuthService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestHeader("X-Session-Id") String sessionId,
            @RequestBody AuthRequest request
    ) {

        String tokenCatedra = catedraAuthService.autenticar(
                request.getUsername(),
                request.getPassword()
        );

        sesionTokenService.guardarToken(sessionId, tokenCatedra);

        return ResponseEntity.ok(Map.of(
                "resultado", true,
                "descripcion", "Login exitoso",
                "sessionId", sessionId
        ));
    }
}

