package commerce.backend.service;

import commerce.backend.entity.Usuario;

public interface IUsuarioService {
    public Usuario findByUsername(String username);
}
