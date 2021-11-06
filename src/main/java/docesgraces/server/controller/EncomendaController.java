package docesgraces.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import docesgraces.server.dto.MailerRequest;
import docesgraces.server.dto.MessageResponse;
import docesgraces.server.service.MailerService;

@RestController
@CrossOrigin
@RequestMapping("/encomendas")
public class EncomendaController {
	
	@Value("${spring.mail.username}")
	private String email;

	@Autowired
	private MailerService mailerService;

	@PostMapping
	public ResponseEntity<?> encomendar(@RequestBody MailerRequest mailerRequest) {
		try {
			String mensagemString = String.format(
					"<p>Enviada por: %s</p>\n" + 
					"<p>Email: %s</p>\n" + 
					"<p>Telefone: %s</p>\n" + 
					"<p>Mensagem:</p>\n" + 
					"<p>%s</p>",
					mailerRequest.getNome(), mailerRequest.getEmail(), mailerRequest.getTelefone(),
					mailerRequest.getMensagem());
			String resposta = mailerService.enviarEmail(email, mailerRequest.getAssunto(),
					mensagemString);
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(resposta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);

		}
	}
}