package br.com.caelum.forum.security.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import br.com.caelum.forum.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenManager {

	@Value("${alura.forum.jwt.secret}")
	private String secret;

	@Value("${alura.forum.jwt.expiration}")
	private long expirationInMillis;

	public String generateToken(Authentication authentication) {
		User user = (User) authentication.getPrincipal();

		final Date hoje = new Date();
		final Date expiration = new Date(hoje.getTime() + this.expirationInMillis);

		return Jwts.builder().setIssuer("Alura Forum").setSubject(Long.toString(user.getId())).setIssuedAt(hoje)
				.setExpiration(expiration).signWith(SignatureAlgorithm.HS256, this.secret).compact();
	}

	public boolean isValid(String jwt) {
		
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(jwt);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
		
	}

	public Long getUserIdFromToken(String jwt) {
		Claims payload = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(jwt).getBody();
		return Long.parseLong(payload.getSubject());
	}

}
