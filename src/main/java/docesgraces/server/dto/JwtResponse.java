package docesgraces.server.dto;

import docesgraces.server.model.Usuario;
import lombok.Getter;

@Getter
public class JwtResponse {
	private String tipo;
	private String email;
	private String nome;
	private String token;

	public JwtResponse(String email, String nome, String token, String tipo) {

		this.email = email;
		this.nome = nome;
		this.token = token;
		this.tipo = tipo;
	}

	public JwtResponse() {
	}

	public static JwtResponse toDTO(Usuario user, String tipo) {
		return new JwtResponse(user.getEmail(), user.getNome(), user.getToken(), tipo);
	}

}