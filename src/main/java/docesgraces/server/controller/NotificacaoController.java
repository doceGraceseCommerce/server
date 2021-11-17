package docesgraces.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import docesgraces.server.dto.MessageResponse;
import docesgraces.server.service.TelegramBotService;
import docesgraces.server.service.TwilioService;

@RestController
@CrossOrigin
@RequestMapping("/notificacao")
public class NotificacaoController {

	@Autowired
	private TwilioService twilioService;

	@Autowired
	private TelegramBotService telegramBotService;

	@PostMapping
	public ResponseEntity<?> whatsapp() {
		try {
			twilioService.setMensagem("testando");
			twilioService.enviarWhatsAppMessage();
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO("Enviada"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);

		}
	}

	@GetMapping
	public ResponseEntity<?> bot() {
		try {
			telegramBotService.setMensagem("testando");
			telegramBotService.enviarMensagem("bot");
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO("Enviada"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

}
