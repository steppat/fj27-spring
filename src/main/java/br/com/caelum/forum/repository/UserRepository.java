package br.com.caelum.forum.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import br.com.caelum.forum.model.User;

public interface UserRepository extends Repository<User, Long>{

	Optional<User> findByEmail(String email);

	Optional<User> findById(Long userId);

	User save(User user);
}
