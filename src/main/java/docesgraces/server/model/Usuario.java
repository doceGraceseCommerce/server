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

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.engine.internal.CascadePoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private int usuarioTipo;

	@Column(unique = true, nullable = false, length = 255)
	private String email;

	@Column(nullable = false, length = 50)
	private String senha;

	@Column(nullable = false, length = 200)
	private String nome;

	@Column(nullable = false, length = 15)
	private String telefone;

	@Column(nullable = false, length = 10)
	private String dataNascimento;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
//	@OnDelete(action = OnDeleteAction.CASCADE)
	private EnderecoUsuario endereco;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id")
//	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private List<Pedido> pedidos = new ArrayList<>();

	private String token;

}
