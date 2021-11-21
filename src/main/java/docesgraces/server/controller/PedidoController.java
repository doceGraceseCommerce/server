package docesgraces.server.controller;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
				List<Pedido> pedidos = pedidoRepository.findAll();
//				return new ResponseEntity<String>(palavra, HttpStatus.OK);
				return new ResponseEntity<List<Pedido>>(pedidos, HttpStatus.OK);
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

	@GetMapping("/usuario_id")
	public ResponseEntity<?> listarPedidosByUsuarioId(@RequestHeader String Authorization) {
		try {
			String usuarioId = authenticationService.validar(Authorization);
			Usuario usuario = usuarioRepository.findById(Long.parseLong(usuarioId)).get();

			List<Pedido> pedidos = pedidoRepository.findByUsuarioId(Long.parseLong(usuarioId));
//				return new ResponseEntity<String>(palavra, HttpStatus.OK);
			return new ResponseEntity<List<Pedido>>(pedidos, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/{pedido_num}")
	public ResponseEntity<?> obterPedido(@PathVariable(value = "pedido_num") String pedidoNum,
			@RequestHeader String Authorization) {
		try {
			String usuarioId = authenticationService.validar(Authorization);
			Usuario usuario = usuarioRepository.findById(Long.parseLong(usuarioId)).get();

			Pedido pedido = pedidoRepository.findByPedidoNum(pedidoNum).get();
//				return new ResponseEntity<String>(palavra, HttpStatus.OK);
			return new ResponseEntity<Pedido>(pedido, HttpStatus.OK);

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

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			String date = dtf.format(now);

			System.out.println(date);

			pedido.setUsuario(usuario);
			pedido.setDataPedido(date);
			pedido.setStatusPedido("Aguardando Pagamento");
			Pedido novoPedido = pedidoRepository.save(pedido);
//			pedidoRepository;
//			"Pedido criado com sucesso!"
			return new ResponseEntity<Pedido>(novoPedido, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/{pedido_num}/novopedido")
	public ResponseEntity<?> dispararEmailPedidoRecebido(@PathVariable(value = "pedido_num") String pedidoNum,
			@RequestHeader String Authorization) {
		try {
			String usuarioId = authenticationService.validar(Authorization);
			Usuario usuario = usuarioRepository.findById(Long.parseLong(usuarioId)).get();

			Pedido pedido = pedidoRepository.findByPedidoNum(pedidoNum).get();

			mailerService.setEmail(pedido.getUsuario().getEmail());
			mailerService.setAssunto("Pedido no. " + pedido.getPedidoNum() + " - " + "Novo Pedido Recebido");
			mailerService.setMensagemString("Pedido no. " + pedido.getPedidoNum() + " - " + "Novo Pedido Recebido");
			telegramBotService.setMensagem("Pedido no. " + pedido.getPedidoNum() + " - " + "Novo Pedido Recebido");
			mailerService.enviarMensagem("email");
			telegramBotService.enviarMensagem("telegram");

			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO("Pedido alterado com sucesso!"),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PatchMapping("/{pedido_num}/pagamento")
	public ResponseEntity<?> alterarStatusPagamento(@PathVariable(value = "pedido_num") String pedidoNum,
			@RequestHeader String Authorization, @RequestBody Pedido pedidoDetalhes) {
		try {
			String usuarioId = authenticationService.validar(Authorization);
			Usuario usuario = usuarioRepository.findById(Long.parseLong(usuarioId)).get();

			Pedido pedido = pedidoRepository.findByPedidoNum(pedidoNum).get();

			System.out.println(pedidoDetalhes.getStatusPagamento());
			System.out.println(pedido.getStatusPedido());

			if (pedidoDetalhes.getStatusPagamento().equals("paid")
					&& pedido.getStatusPedido().equals("Aguardando Pagamento")) {
				pedido.setStatusPedido("Pagamento Aprovado");
				pedido.setStatusPagamento(pedidoDetalhes.getStatusPagamento());
//				pedido.setPagamentoId(pedidoDetalhes.getPagamentoId());

				mailerService.setEmail(pedido.getUsuario().getEmail());
				mailerService.setAssunto("Pedido no. " + pedido.getPedidoNum() + " - " + "Pagamento Aprovado");
				mailerService.setMensagemString("Pedido no. " + pedido.getPedidoNum() + " - " + "Pagamento Aprovado");
				telegramBotService.setMensagem("Pedido no. " + pedido.getPedidoNum() + " - " + "Pagamento Aprovado");

				pedido.events.subscribe("telegram", telegramBotService);
				pedido.events.subscribe("email", mailerService);

			} else {
				pedido.setStatusPedido("Processando Pagamento");
				pedido.setStatusPagamento(pedidoDetalhes.getStatusPedido());
//				pedido.setPagamentoId(pedidoDetalhes.getPagamentoId());
			}

			pedidoRepository.save(pedido);
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO("Pedido alterado com sucesso!"),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PatchMapping("/{id}/status")
	public ResponseEntity<?> alterarStatusPedido(@PathVariable(value = "id") long id,
			@RequestHeader String Authorization, @RequestBody Pedido pedidoDetalhes) {
		try {
			String usuarioId = authenticationService.validar(Authorization);
			Usuario usuario = usuarioRepository.findById(Long.parseLong(usuarioId)).get();

			if (usuario.getUsuarioTipo() == 1) {
				Pedido pedido = pedidoRepository.findById(id).get();

				mailerService.setEmail(pedido.getUsuario().getEmail());
				mailerService
						.setAssunto("Pedido no. " + pedido.getPedidoNum() + " - " + pedidoDetalhes.getStatusPedido());
				mailerService.setMensagemString(
						"Pedido no. " + pedido.getPedidoNum() + " - " + pedidoDetalhes.getStatusPedido());
				telegramBotService
						.setMensagem("Pedido no. " + pedido.getPedidoNum() + " - " + pedidoDetalhes.getStatusPedido());

				pedido.events.subscribe("telegram", telegramBotService);
				pedido.events.subscribe("email", mailerService);

				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				String date = dtf.format(now);

				if (pedidoDetalhes.getStatusPedido().equals("Pagamento Aprovado")) {
					pedido.setStatusPedido(pedidoDetalhes.getStatusPedido());
				} else if (pedidoDetalhes.getStatusPedido().equals("Separando Pedido")) {
					pedido.setStatusPedido(pedidoDetalhes.getStatusPedido());
				} else if (pedidoDetalhes.getStatusPedido().equals("Pedido Enviado")) {
					pedido.setStatusPedido(pedidoDetalhes.getStatusPedido());
					pedido.setDataEnvio(date);
				} else if (pedidoDetalhes.getStatusPedido().equals("Pedido Entregue")) {
					pedido.setStatusPedido(pedidoDetalhes.getStatusPedido());
					pedido.setDataEntrega(date);
				} else if (pedidoDetalhes.getStatusPedido().equals("Pedido Cancelado")) {
					pedido.setStatusPedido(pedidoDetalhes.getStatusPedido());
				}

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
