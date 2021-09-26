package docesgraces.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import docesgraces.server.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
