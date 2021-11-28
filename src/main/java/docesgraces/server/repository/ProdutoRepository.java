package docesgraces.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import docesgraces.server.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	Optional<Produto> findById(Long id);
	
}
