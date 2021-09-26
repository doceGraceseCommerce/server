package docesgraces.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import docesgraces.server.model.Usuario;
import docesgraces.server.repository.UsuarioRepository;
import docesgraces.server.service.AuthenticationService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private AuthenticationService authenticationService;

//	@GetMapping
//	public List<Usuario> listar() {
//		return usuarioRepository.findAll();
//	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario adicionar(@RequestBody Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario buscarUsuario(@PathVariable(value = "id") long id, @RequestHeader String Authorization) {
		authenticationService.validar(Authorization);
		Usuario usuario = usuarioRepository.findById(id).get();
		return usuario;
	}

	@PatchMapping("/{id}")
	public Usuario alterar(@PathVariable(value = "id") long id, @RequestBody Usuario usuarioDetalhes) {
//		userAuthenticationService.validar(Authorization);
		Usuario usuario = usuarioRepository.findById(id).get();
		usuario.setEmail(usuarioDetalhes.getEmail());
		return usuarioRepository.save(usuario);
	}

	@DeleteMapping("/{id}")
	public String deletar(@PathVariable(value = "id") long id) {
		usuarioRepository.deleteById(id);
		return "Usuário deletado";
	}

}
