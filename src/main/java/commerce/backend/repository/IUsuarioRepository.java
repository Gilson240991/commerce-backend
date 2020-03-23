package commerce.backend.repository;

import commerce.backend.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioRepository extends CrudRepository<Usuario,Long> {
    public Usuario findByUsuario(String usuario);
}
