package br.com.caelum.forum.actuator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import br.com.caelum.forum.model.OpenTopicByCategory;
import br.com.caelum.forum.repository.TopicRepository;

@Endpoint(id="open-topics-by-category")
@Component
public class UnansweredTopicsActuatorEndpoint {


	@Autowired
	 private TopicRepository topicRepository;
	 
	@ReadOperation
    public List<OpenTopicByCategory> execute() {
        return topicRepository.findOpenTopicsByCategory();
    }

	
}
