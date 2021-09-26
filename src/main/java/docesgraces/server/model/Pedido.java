package docesgraces.server.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@NoArgsConstructor
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	private Usuario usuario;

	@Column(nullable = false)
	private double valorTotal;

	@Column(nullable = false)
	private int quantidadeTotal;

	@Column(nullable = false, length = 10)
	private String dataPedido;

	@Column
	private double taxaEntrega;

	@Column(length = 10)
	private String dataEnvio;

	@Column(length = 10)
	private String dataEntrega;

	@OneToOne(cascade = CascadeType.ALL)
	private EnderecoPedido endereco;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "pedido_id")
	private List<ItemPedido> itens = new ArrayList<>();

}