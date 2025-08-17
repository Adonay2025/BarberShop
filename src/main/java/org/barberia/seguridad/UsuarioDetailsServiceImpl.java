package org.barberia.seguridad;

import org.barberia.modelos.Usuarios;
import org.barberia.repositorios.IUsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUsuariosRepository usuariosRepo;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuarios usuario = usuariosRepo.findByCorreo(correo);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        // Crea la autoridad con el rol del usuario
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority("ROLE_" + usuario.getRole().getNombrerol());

        return new User(
                usuario.getCorreo(),
                usuario.getContrasena(),
                Collections.singletonList(authority)
        );
    }
}

