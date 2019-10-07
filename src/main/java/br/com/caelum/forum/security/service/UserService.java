package br.com.caelum.forum.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.caelum.forum.model.User;
import br.com.caelum.forum.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> user = repo.findByEmail(username);
		return user.orElseThrow(() -> {
			return new UsernameNotFoundException("Não foi possível encontrar o usuario " + username);
		});
	}

	public UserDetails loadUserByUsername(Long userId) {
		Optional<User> possibleUser = repo.findById(userId);

		return possibleUser.orElseThrow(() -> {
			return new UsernameNotFoundException("Não foi possível encontrar o usuario com a Id " + userId);
		});
	}
}
