package docesgraces.server.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import docesgraces.server.dto.MessageResponse;
import docesgraces.server.model.Usuario;
import docesgraces.server.repository.UsuarioRepository;
import docesgraces.server.service.AuthenticationService;

@RestController
@CrossOrigin
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private AuthenticationService authenticationService;

	@GetMapping
	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> adicionar(@RequestBody Usuario usuario) {
		try {
			usuarioRepository.save(usuario);
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO("Usuário criado com sucesso!"),
					HttpStatus.CREATED);
		} catch (DataAccessException e) {
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/id")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> buscarUsuario(@RequestHeader String Authorization) {
		try {
			String id = authenticationService.validar(Authorization);
			Usuario usuario = usuarioRepository.findById(Long.parseLong(id)).get();
			return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Map<String, String> resp = new HashMap<>();
			resp.put("message", e.getMessage());
			return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
		}
	}

	@PatchMapping("/id")
	public ResponseEntity<?> alterar(@RequestHeader String Authorization, @RequestBody Usuario usuarioDetalhes) {
		try {
			String id = authenticationService.validar(Authorization);
			Usuario usuario = usuarioRepository.findById(Long.parseLong(id)).get();
			usuario.setEmail(usuarioDetalhes.getEmail());
			usuarioRepository.save(usuario);
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO("Usuário alterado com sucesso!"),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/id")
	public ResponseEntity<?> deletar(@RequestHeader String Authorization) {
		try {
			String id = authenticationService.validar(Authorization);
			usuarioRepository.deleteById(Long.parseLong(id));
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO("Usuário excluído com sucesso!"),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

}
