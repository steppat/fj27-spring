package br.com.caelum.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.caelum.forum.model.Answer;
import br.com.caelum.forum.model.Topic;
import br.com.caelum.forum.model.User;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
	
	List<Answer> findByOwner(User owner);
	
	List<Answer> findByTopic(Topic topic);
	
}
