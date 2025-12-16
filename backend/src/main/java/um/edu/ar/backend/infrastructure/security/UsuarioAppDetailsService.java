package um.edu.ar.backend.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import um.edu.ar.backend.infrastructure.persistence.repository.UsuarioAppRepository;

@Service
@RequiredArgsConstructor
public class UsuarioAppDetailsService implements UserDetailsService {

    private final UsuarioAppRepository usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var usuario = usuarioRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        return new UsuarioAppDetails(usuario);
    }
}
