

package um.edu.ar.backend.infrastructure.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import um.edu.ar.backend.infrastructure.persistence.repository.JpaAsientoBloqueadoRepository;

@RestController
@RequiredArgsConstructor
public class DebugAsientoController {

    private final JpaAsientoBloqueadoRepository repo;

    @GetMapping("/debug/asientos-bloqueados")
    public Object listar() {
        return repo.findAll();
    }
}

