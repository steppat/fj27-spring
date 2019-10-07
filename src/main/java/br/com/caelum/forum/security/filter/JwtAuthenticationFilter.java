package br.com.caelum.forum.security.filter;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.caelum.forum.security.jwt.TokenManager;
import br.com.caelum.forum.security.service.UserService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private TokenManager manager;
	private UserService service;

	public JwtAuthenticationFilter(TokenManager manager, UserService service) {
		this.manager = manager;
		this.service = service;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwt = getTokenFromRequest(request);
		
		
		if(manager.isValid(jwt)) {
			Long userId = manager.getUserIdFromToken(jwt);
			UserDetails userDetails = this.service.loadUserByUsername(userId);
			
			Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
			System.out.println(authorities);
			Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
	}

	private String getTokenFromRequest(HttpServletRequest request) {

		String bearerToken = request.getHeader("Authorization");
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7,bearerToken.length());
		}
		
		return null;
	}

}
