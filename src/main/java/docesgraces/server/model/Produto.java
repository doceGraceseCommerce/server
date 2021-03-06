package docesgraces.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "produto")
@Getter
@Setter
@NoArgsConstructor
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 255)
	private String nome;

	@Column(nullable = false)
	private String descricao;

	@Column(nullable = false)
	private String infNutricionaisProduto;

	@Column(nullable = false, length = 255)
	private String imagem;

	@Column(nullable = false)
	private double preco;

	@Column(nullable = false)
	private int quantidade;

	@Column(nullable = false)
	private boolean produtoAtivo;

}
