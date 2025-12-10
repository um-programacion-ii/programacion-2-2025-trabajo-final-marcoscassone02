package um.edu.ar.backend.infrastructure.persistence.repository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import um.edu.ar.backend.infrastructure.persistence.entity.UsuarioApp;

@Configuration
public class UsuarioInitConfig {

    @Bean
    CommandLineRunner initUsuarios(UsuarioAppRepository repo) {
        return args -> {

            repo.deleteAll();

            if (repo.findByUsername("marcos").isEmpty()) {
                UsuarioApp u = new UsuarioApp();
                u.setUsername("marcos");
                u.setPassword("marcos");
                repo.save(u);
                System.out.println(">>> Usuario 'marcos' creado en usuarios_app (password en texto plano)");
            }

            System.out.println(">>> Usuarios en BD:");
            repo.findAll().forEach(u -> {
                System.out.println(" - " + u.getUsername() + " | " + u.getPassword());
            });
        };
    }
}
