package um.edu.ar.backend.infrastructure.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import um.edu.ar.backend.infrastructure.persistence.entity.UsuarioApp;

import java.util.Collection;
import java.util.List;

public class UsuarioAppDetails implements UserDetails {

    private final UsuarioApp usuario;

    public UsuarioAppDetails(UsuarioApp usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    public Long getId() {
        return usuario.getId();
    }
}
