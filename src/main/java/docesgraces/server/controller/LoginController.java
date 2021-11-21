package docesgraces.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import docesgraces.server.dto.JwtResponse;
import docesgraces.server.dto.MessageResponse;
import docesgraces.server.model.Usuario;
import docesgraces.server.repository.UsuarioRepository;
import docesgraces.server.service.AuthenticationService;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping
	public ResponseEntity<?> autenticar(@RequestBody Usuario usuario) {
		try {
			Usuario user = authenticationService.autenticar(usuario);
			return new ResponseEntity<JwtResponse>(JwtResponse.toDTO(user, "Bearer "), HttpStatus.CREATED);
		} catch (Exception e) {
//			Map<String, String> resp = new HashMap<>();
//			resp.put("message", e.getLocalizedMessage());
//			return new ResponseEntity<Map<String, String>>(resp, HttpStatus.UNAUTHORIZED);
//			System.out.println(e.getMessage());
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO("E-mail ou senha inválidos"),
					HttpStatus.BAD_REQUEST);

		}

	}

	@PostMapping("/admin")
	public ResponseEntity<?> autenticarAdmin(@RequestBody Usuario usuario) {
		try {
			Usuario user = authenticationService.autenticar(usuario);
			if (user.getUsuarioTipo() == 1) {
				return new ResponseEntity<JwtResponse>(JwtResponse.toDTO(user, "Bearer "), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<MessageResponse>(MessageResponse.toDTO("Usuário não possui autorização!"),
						HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
//			Map<String, String> resp = new HashMap<>();
//			resp.put("message", e.getLocalizedMessage());
//			return new ResponseEntity<Map<String, String>>(resp, HttpStatus.UNAUTHORIZED);
//			System.out.println(e.getMessage());
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO("E-mail ou senha inválidos"),
					HttpStatus.BAD_REQUEST);

		}

	}
}
