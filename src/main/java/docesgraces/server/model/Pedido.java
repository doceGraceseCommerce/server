package docesgraces.server.model;

import java.io.Serializable;
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
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import docesgraces.server.listener.EventManager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//@NoArgsConstructor
//@EntityListeners(ProdutoListener.class)
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

	@Transient
	@JsonIgnore
	public EventManager events;

	public Pedido() {
		this.events = new EventManager("telegram", "email");
	}

	@PostPersist
	public void afterPresist() {
		try {
			events.notify("telegram");
			events.notify("email");
//			TelegramBotService telegram = new TelegramBotService();
//			telegram.setLog("teste");
//			telegram.update("save", "teste");
			System.out.println("salvou");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}