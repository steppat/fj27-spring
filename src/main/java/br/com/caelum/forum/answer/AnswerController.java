package br.com.caelum.forum.answer;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.forum.answer.dto.RegisterNewAnswerInputDto;
import br.com.caelum.forum.answer.dto.RegisterNewAnswerOutputDto;
import br.com.caelum.forum.answer.service.NewReplyProcessorService;
import br.com.caelum.forum.model.Answer;
import br.com.caelum.forum.model.Topic;
import br.com.caelum.forum.model.User;
import br.com.caelum.forum.repository.TopicRepository;

@RestController
public class AnswerController {

	@Autowired
	private TopicRepository topicRepository;
	
	@Autowired
    private NewReplyProcessorService newReplyProcessorService;

	
	@CacheEvict(value = "topicById", key="#id")
	@PostMapping(value = "/api/topics/{id}/answers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegisterNewAnswerOutputDto> registerNewAnswer(@RequestBody @Valid RegisterNewAnswerInputDto dto,
			@PathVariable("id") long id, @AuthenticationPrincipal User loggedUser, UriComponentsBuilder uriBuilder) {

		if(loggedUser == null) {
			throw new RuntimeException( "AnwerController, no user logado");
		}
		
		Topic topic = this.topicRepository.getOne(id);

		Answer answer = new Answer(dto.getContent(), topic, loggedUser);

		this.newReplyProcessorService.execute(answer);

		URI path = uriBuilder.path("/api/topics/{id}/answer/{idAnswer}").buildAndExpand(topic.getId(), answer.getId())
				.toUri();

		return ResponseEntity.created(path).body(new RegisterNewAnswerOutputDto(answer));
	}

}
