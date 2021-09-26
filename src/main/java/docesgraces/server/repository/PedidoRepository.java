package docesgraces.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import docesgraces.server.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
