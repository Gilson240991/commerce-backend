package commerce.backend.service;

import commerce.backend.entity.Usuario;
import commerce.backend.repository.IUsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService,IUsuarioService {

    private Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByUsuario(username);
        if(usuario == null){
            logger.error("No existe el usuario "+username+"en el sistema");
            throw new UsernameNotFoundException("No existe el usuario "+username+" en el sistema");
        }
        List<GrantedAuthority> authorities = usuario.getRoles()
                .stream()
                .map(role->new SimpleGrantedAuthority(role.getNombre()))
                .peek(authority->logger.info("Rol: "+ authority.getAuthority()))
                .collect(Collectors.toList());

        return new User(usuario.getUsuario(),usuario.getPassword(),usuario.getEstado(),true,true,true, authorities);
    }

    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsuario(username);
    }
}
