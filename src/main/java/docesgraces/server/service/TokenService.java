package docesgraces.server.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import docesgraces.server.model.Usuario;

import java.util.Date;

@Service
public class TokenService {

	@Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Usuario usuario) {
    	Date hoje = new Date();
    	Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
        return Jwts.builder()
        		.setSubject(usuario.getId().toString())
                .setIssuer("API")
                .setIssuedAt(hoje)
                .setExpiration(dataExpiracao)
                .claim("usuarioTipo", usuario.getUsuarioTipo())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Claims decodeToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

}
