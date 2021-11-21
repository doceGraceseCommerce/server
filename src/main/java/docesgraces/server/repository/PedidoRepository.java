package docesgraces.server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import docesgraces.server.model.Pedido;
import docesgraces.server.model.Usuario;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	Optional<Pedido> findById(Long id);

	Optional<Pedido> findByPedidoNum(String pedidoNum);
	
	List<Pedido> findByUsuarioId(Long id);

}
