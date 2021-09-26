package docesgraces.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import docesgraces.server.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findById(Long id);

	Usuario findByEmail(String email);

}
