package docesgraces.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import docesgraces.server.model.Produto;
import docesgraces.server.model.Usuario;
import docesgraces.server.repository.ProdutoRepository;
import docesgraces.server.repository.UsuarioRepository;
import docesgraces.server.service.AuthenticationService;

@RestController
@CrossOrigin
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private AuthenticationService authenticationService;

	@GetMapping
	public List<Produto> listar() {
		return produtoRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> adicionar(@RequestHeader String Authorization, @RequestBody Produto produto) {
		try {
			String usuarioId = authenticationService.validar(Authorization);
			Usuario usuario = usuarioRepository.findById(Long.parseLong(usuarioId)).get();
			if (usuario.getUsuarioTipo() == 1) {
				System.out.println(produto.getInfNutricionaisProduto());
				System.out.println(produto.getNome());
				System.out.println(produto.getDescricao());
				produtoRepository.save(produto);
				return new ResponseEntity<MessageResponse>(MessageResponse.toDTO("Produto criado com sucesso!"),
						HttpStatus.CREATED);
			} else {
				return new ResponseEntity<MessageResponse>(
						MessageResponse.toDTO("Usuário não possui autorização para cadastrar novos produtos!"),
						HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> alterarProduto(@PathVariable(value = "id") long id, @RequestHeader String Authorization,
			@RequestBody Produto produtoDetalhes) {
		try {
			String usuarioId = authenticationService.validar(Authorization);
			Usuario usuario = usuarioRepository.findById(Long.parseLong(usuarioId)).get();
			if (usuario.getUsuarioTipo() == 1) {
//				Produto produto = produtoRepository.findById(id).get();
				
				produtoRepository.save(produtoDetalhes);
				return new ResponseEntity<MessageResponse>(MessageResponse.toDTO("Produto alterado com sucesso!"),
						HttpStatus.CREATED);
			} else {
				return new ResponseEntity<MessageResponse>(
						MessageResponse.toDTO("Usuário não possui autorização para alterar produtos!"),
						HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PatchMapping("/{id}/quantidade")
	public ResponseEntity<?> alterarQuantidade(@PathVariable(value = "id") long id, @RequestHeader String Authorization,
			@RequestBody Produto produtoDetalhes) {
		try {
			String usuarioId = authenticationService.validar(Authorization);
			Usuario usuario = usuarioRepository.findById(Long.parseLong(usuarioId)).get();
			if (usuario.getUsuarioTipo() == 1) {
				Produto produto = produtoRepository.findById(id).get();
				produto.setQuantidade(produtoDetalhes.getQuantidade());
				produtoRepository.save(produto);
				return new ResponseEntity<MessageResponse>(MessageResponse.toDTO("Produto alterado com sucesso!"),
						HttpStatus.CREATED);
			} else {
				return new ResponseEntity<MessageResponse>(
						MessageResponse.toDTO("Usuário não possui autorização para alterar produtos!"),
						HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PatchMapping("/{id}/preco")
	public ResponseEntity<?> alterarPreco(@PathVariable(value = "id") long id, @RequestHeader String Authorization,
			@RequestBody Produto produtoDetalhes) {
		try {
			String usuarioId = authenticationService.validar(Authorization);
			Usuario usuario = usuarioRepository.findById(Long.parseLong(usuarioId)).get();
			if (usuario.getUsuarioTipo() == 1) {
				Produto produto = produtoRepository.findById(id).get();
				produto.setPreco(produtoDetalhes.getPreco());
				produtoRepository.save(produto);
				return new ResponseEntity<MessageResponse>(MessageResponse.toDTO("Produto alterado com sucesso!"),
						HttpStatus.CREATED);
			} else {
				return new ResponseEntity<MessageResponse>(
						MessageResponse.toDTO("Usuário não possui autorização para alterar produtos!"),
						HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PatchMapping("/{id}/status")
	public ResponseEntity<?> alterarStatus(@PathVariable(value = "id") long id, @RequestHeader String Authorization,
			@RequestBody Produto produtoDetalhes) {
		try {
			String usuarioId = authenticationService.validar(Authorization);
			Usuario usuario = usuarioRepository.findById(Long.parseLong(usuarioId)).get();
			if (usuario.getUsuarioTipo() == 1) {
				Produto produto = produtoRepository.findById(id).get();
				produto.setProdutoAtivo(produtoDetalhes.isProdutoAtivo());
				produtoRepository.save(produto);
				return new ResponseEntity<MessageResponse>(MessageResponse.toDTO("Produto alterado com sucesso!"),
						HttpStatus.CREATED);
			} else {
				return new ResponseEntity<MessageResponse>(
						MessageResponse.toDTO("Usuário não possui autorização para alterar produtos!"),
						HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PatchMapping("/{id}/deletado")
	public ResponseEntity<?> alterarDeletado(@PathVariable(value = "id") long id, @RequestHeader String Authorization,
			@RequestBody Produto produtoDetalhes) {
		try {
			String usuarioId = authenticationService.validar(Authorization);
			Usuario usuario = usuarioRepository.findById(Long.parseLong(usuarioId)).get();
			if (usuario.getUsuarioTipo() == 1) {
				Produto produto = produtoRepository.findById(id).get();
				produto.setProdutoEncerrado(produtoDetalhes.isProdutoEncerrado());
				produtoRepository.save(produto);
				return new ResponseEntity<MessageResponse>(MessageResponse.toDTO("Produto alterado com sucesso!"),
						HttpStatus.CREATED);
			} else {
				return new ResponseEntity<MessageResponse>(
						MessageResponse.toDTO("Usuário não possui autorização para alterar produtos!"),
						HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable(value = "id") long id, @RequestHeader String Authorization) {
		try {
			String usuarioId = authenticationService.validar(Authorization);
			Usuario usuario = usuarioRepository.findById(Long.parseLong(usuarioId)).get();
			if (usuario.getUsuarioTipo() == 1) {
				produtoRepository.deleteById(id);
				return new ResponseEntity<MessageResponse>(MessageResponse.toDTO("Produto deletado com sucesso!"),
						HttpStatus.CREATED);
			} else {
				return new ResponseEntity<MessageResponse>(
						MessageResponse.toDTO("Usuário não possui autorização para deletar produtos!"),
						HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			return new ResponseEntity<MessageResponse>(MessageResponse.toDTO(e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

}
