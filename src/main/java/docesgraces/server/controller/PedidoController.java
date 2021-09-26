package docesgraces.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import docesgraces.server.model.Pedido;
import docesgraces.server.model.Produto;
import docesgraces.server.repository.PedidoRepository;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;

	@GetMapping
	public List<Pedido> listar() {
		return pedidoRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Pedido adicionar(@RequestBody Pedido pedido) {
//		UsuarioRepository usuarioRepository;
//		Usuario usuario = usuarioRepository.findById(pedido.getUsuario().getId()).get();
////		UsuarioRepository usuarioRepository;
//		pedido.setUsuario(usuario);
		return pedidoRepository.save(pedido);
	}

	@PatchMapping("/{id}")
	public Pedido alterar(@PathVariable(value = "id") long id, @RequestBody Pedido pedidoDetalhes) {
		Pedido pedido = pedidoRepository.findById(id).get();
		pedido.setDataEntrega(pedidoDetalhes.getDataEntrega());
		return pedidoRepository.save(pedido);
	}
}
