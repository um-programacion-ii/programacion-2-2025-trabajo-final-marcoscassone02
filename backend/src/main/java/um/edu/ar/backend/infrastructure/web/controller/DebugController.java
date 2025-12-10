package um.edu.ar.backend.infrastructure.web.controller;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DebugController {

    private final EntityManager em;

    @GetMapping("/debug/tablas")
    public List<String> debugTablas() {
        return em.createNativeQuery("SHOW TABLES")
                .getResultList()
                .stream()
                .map(row -> ((Object[]) row)[0].toString())
                .toList();
    }
}
