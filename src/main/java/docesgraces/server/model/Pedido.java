package docesgraces.server.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

	@Column
	private String pedidoNum;

	@ManyToOne
	private Usuario usuario;

	@Column(nullable = false)
	private double valorTotal;

	@Column(nullable = false)
	private String dataPedido;

	@Column
	private double taxaEntrega;

	@Column
	private String dataEnvio;

	@Column
	private String dataEntrega;

	@Column
	private String pagamentoId;

	@Column
	private String statusPagamento;

	@Column
	private String statusPedido;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "endereco_id")
	private EnderecoPedido endereco;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "pedido_id")
	private List<ItemPedido> itens = new ArrayList<>();

	@Transient
	@JsonIgnore
	public EventManager events;

	public Pedido() {
		this.events = new EventManager("telegram", "email");
	}

	@PostUpdate
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