package um.edu.ar.backend.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import um.edu.ar.backend.infrastructure.persistence.entity.UsuarioApp;
import um.edu.ar.backend.infrastructure.persistence.repository.UsuarioAppRepository;
import um.edu.ar.backend.infrastructure.web.dto.CreateUserRequest;

@RestController
@RequestMapping("/debug/usuarios")
@RequiredArgsConstructor
public class DebugUsuarioController {

    private final UsuarioAppRepository repo;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CreateUserRequest req) {
        if (req.username() == null || req.username().isBlank()
                || req.password() == null || req.password().isBlank()) {
            return ResponseEntity.badRequest().body("username y password son obligatorios");
        }

        if (repo.findByUsername(req.username()).isPresent()) {
            return ResponseEntity.status(409).body("Ya existe: " + req.username());
        }

        UsuarioApp u = new UsuarioApp();
        u.setUsername(req.username());
        u.setPassword(passwordEncoder.encode(req.password()));
        repo.save(u);

        return ResponseEntity.ok("Creado: " + req.username());
    }
}
