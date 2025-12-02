package um.edu.ar.backend.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.edu.ar.backend.domain.ports.out.SesionService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sesion")
@RequiredArgsConstructor
public class SesionController {

    private final SesionService sesionService;

    @PostMapping("/iniciar")
    public ResponseEntity<Map<String, String>> iniciar() {
        String sessionId = sesionService.crearSesion();

        Map<String, String> r = new HashMap<>();
        r.put("sessionId", sessionId);

        return ResponseEntity.ok(r);
    }
}

