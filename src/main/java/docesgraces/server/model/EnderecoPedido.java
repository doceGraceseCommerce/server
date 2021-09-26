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
@Table(name = "endereco_pedido")
@Getter
@Setter
@NoArgsConstructor
public class EnderecoPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 255)
	private String logradouro;

	@Column(nullable = false, length = 20)
	private String numero;

	@Column(length = 255)
	private String complemento;

	@Column(nullable = false, length = 255)
	private String bairro;

	@Column(nullable = false, length = 255)
	private String cidade;

	@Column(nullable = false, length = 10)
	private String uf;

	@Column(nullable = false, length = 200)
	private String cep;
}
