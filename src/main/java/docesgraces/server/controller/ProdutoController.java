package docesgraces.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import docesgraces.server.model.Produto;
import docesgraces.server.repository.ProdutoRepository;

@RestController
@CrossOrigin
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@GetMapping
	public List<Produto> listar() {
		return produtoRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Produto adicionar(@RequestBody Produto produto) {
		return produtoRepository.save(produto);
	}

	@PatchMapping("/{id}")
	public Produto alterar(@PathVariable(value = "id") long id, @RequestBody Produto produtoDetalhes) {
		Produto produto = produtoRepository.findById(id).get();
		produto.setPreco(produtoDetalhes.getPreco());
		return produtoRepository.save(produto);
	}

	@DeleteMapping("/{id}")
	public String deletar(@PathVariable(value = "id") long id) {
		produtoRepository.deleteById(id);
		return "Produto deletado";
	}

}
