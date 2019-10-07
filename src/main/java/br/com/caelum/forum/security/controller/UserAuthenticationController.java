package br.com.caelum.forum.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.caelum.forum.security.dto.input.LoginInputDto;
import br.com.caelum.forum.security.dto.output.AuthenticationTokenOutputDto;
import br.com.caelum.forum.security.jwt.TokenManager;

@RestController
@RequestMapping("/api/auth")
public class UserAuthenticationController {
	
	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private TokenManager tokenManager;

	@PostMapping
	public ResponseEntity<AuthenticationTokenOutputDto> authenticate(@RequestBody LoginInputDto dto) {
		
		UsernamePasswordAuthenticationToken authenticationToken = dto.build();
	
		try {
			Authentication authentication = this.authManager.authenticate(authenticationToken);
			
			String token = this.tokenManager.generateToken(authentication);
			
			AuthenticationTokenOutputDto response = new AuthenticationTokenOutputDto("Bearer", token);

			return ResponseEntity.ok(response);
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}	
		
	}
	
	
}
