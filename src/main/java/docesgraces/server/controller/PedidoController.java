package docesgraces.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import docesgraces.server.dto.MessageResponse;
import docesgraces.server.model.Pedido;
import docesgraces.server.model.Usuario;
import docesgraces.server.repository.PedidoRepository;
import docesgraces.server.repository.UsuarioRepository;
import docesgraces.server.service.AuthenticationService;
import docesgraces.server.service.MailerService;
import docesgraces.server.service.TelegramBotService;

@RestController
@CrossOrigin
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private TelegramBotService telegramBotService;

	@Autowired
	private MailerService mailerService;

	@GetMapping
	public ResponseEntity<?> listar(@RequestHeader String Authorization) {
		try {
			String usuarioId = authenticationService.validar(Authorization);
			Usuario usuario = usuarioRepository.findById(Long.parseLong(usuarioId)).get();
			if (usuario.getUsuarioTipo() == 1) {
				return new ResponseEntity<List<Pedido>>(pedidoRepository.findAll(), HttpStatus.OK);
			} else {
				return new ResponseEntity<MessageResponse>(
						MessageResponse.toDTO("Usuário não possui autorização para listar todos os pedidos!"),
						HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> adicionar(@RequestHeader String Authorization, @RequestBody Pedido pedido) {

		try {
			String usuarioId = authenticationService.validar(Authorization);
			Usuario usuario = usuarioRepository.findById(Long.parseLong(usuarioId)).get();

//			mailerService.setEmail(usuario.getEmail());
			mailerService.setAssunto("Pedido Recebido");
			mailerService.setMensagemString("Novo pedido OK!");
			telegramBotService.setMensagem("Novo pedido OK!");
			pedido.events.subscribe("telegram", telegramBotService);
			pedido.events.subscribe("email", mailerService);

			pedido.setUsuario(usuario);
			pedidoRepository.save(pedido);
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO("Pedido criado com sucesso!"),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> alterar(@PathVariable(value = "id") long id, @RequestHeader String Authorization,
			@RequestBody Pedido pedidoDetalhes) {
		try {
			String usuarioId = authenticationService.validar(Authorization);
			Usuario usuario = usuarioRepository.findById(Long.parseLong(usuarioId)).get();
			if (usuario.getUsuarioTipo() == 1) {
				Pedido pedido = pedidoRepository.findById(id).get();
				pedido.setDataEntrega(pedidoDetalhes.getDataEntrega());
				pedidoRepository.save(pedido);
				return new ResponseEntity<MessageResponse>(MessageResponse.toDTO("Pedido alterado com sucesso!"),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<MessageResponse>(
						MessageResponse.toDTO("Usuário não possui autorização para alterar pedidos!"),
						HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}
}
