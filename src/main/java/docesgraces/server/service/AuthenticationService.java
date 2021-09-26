package docesgraces.server.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import docesgraces.server.exception.ExpiredTokenException;
import docesgraces.server.exception.InvalidLoginException;
import docesgraces.server.exception.InvalidTokenException;
import docesgraces.server.model.Usuario;
import docesgraces.server.repository.UsuarioRepository;
import io.jsonwebtoken.Claims;

@Service
public class AuthenticationService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TokenService tokenService;

	public Usuario autenticar(Usuario usuario) {
		Usuario usuarioDetalhes = usuarioRepository.findByEmail(usuario.getEmail());
		if (usuario.getEmail().equals(usuarioDetalhes.getEmail())
				&& usuario.getSenha().equals(usuarioDetalhes.getSenha())) {
			String token = tokenService.generateToken(usuarioDetalhes);
			System.out.println(token);
			usuarioDetalhes.setToken(token);
			return usuarioDetalhes;
		} else {
			throw new InvalidLoginException();
		}
	}

	public boolean validar(String token) {
		try {
			String tokenTratado = token.replace("Bearer ", "");
			Claims claims = tokenService.decodeToken(tokenTratado);

			System.out.println(claims.getIssuer());
			System.out.println(claims.getIssuedAt());
			// Verifica se o token está expirado
			if (claims.getExpiration().before(new Date(System.currentTimeMillis())))
				throw new ExpiredTokenException();
			System.out.println(claims.getExpiration());
			return true;
		} catch (ExpiredTokenException et) {
			et.printStackTrace();
			throw et;
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvalidTokenException();
		}

	}

}
