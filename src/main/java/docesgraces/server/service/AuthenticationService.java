package docesgraces.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import docesgraces.server.exception.ExpiredTokenException;
import docesgraces.server.exception.InvalidLoginException;
import docesgraces.server.exception.InvalidTokenException;
import docesgraces.server.model.Usuario;
import docesgraces.server.repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

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
			throw new InvalidLoginException("E-mail ou senha inválido");
		}
	}

	public String validar(String token) {
		try {
			String tokenTratado = token.replace("Bearer ", "");
			Claims claims = tokenService.decodeToken(tokenTratado);
			System.out.println(claims.getIssuer());
			System.out.println(claims.getIssuedAt());
			System.out.println(claims.getExpiration());
			return claims.getSubject();
		} catch (ExpiredJwtException et) {
//			et.printStackTrace();
			System.out.println(et.getMessage());
			throw new ExpiredTokenException("Token expirado");
		} catch (MalformedJwtException e) {
//			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new InvalidTokenException("Token inválido");
		}
	}

}
