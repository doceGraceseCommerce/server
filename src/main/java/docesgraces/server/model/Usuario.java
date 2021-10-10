package docesgraces.server.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
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

	@OneToOne(cascade = CascadeType.ALL)
	private EnderecoUsuario endereco;
//	@ManyToMany(fetch = FetchType.EAGER)
//	private Set<Perfil> perfis;

	private String token;

}
