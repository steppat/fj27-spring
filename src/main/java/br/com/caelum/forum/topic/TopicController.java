package br.com.caelum.forum.topic;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.forum.model.Category;
import br.com.caelum.forum.model.Topic;
import br.com.caelum.forum.model.User;
import br.com.caelum.forum.repository.CategoryRepository;
import br.com.caelum.forum.repository.CourseRepository;
import br.com.caelum.forum.repository.TopicRepository;
import br.com.caelum.forum.topic.dto.DashboardDto;
import br.com.caelum.forum.topic.dto.ListDto;
import br.com.caelum.forum.topic.dto.NewTopicInputDto;
import br.com.caelum.forum.topic.dto.TopicBriefOutputDto;
import br.com.caelum.forum.topic.dto.TopicByIdOutputDto;
import br.com.caelum.forum.topic.dto.TopicOutputDto;
import br.com.caelum.forum.topic.dto.TopicSearchInputDto;
import br.com.caelum.forum.topic.valiador.NewTopicCustomValidator;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

	@Autowired
	private TopicRepository topicRepository;

	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;


	@InitBinder("newTopicInputDto")
	public void initBinder(WebDataBinder binder, @AuthenticationPrincipal User loggedUser) {
		binder.addValidators(new NewTopicCustomValidator(this.topicRepository, loggedUser));
	}

	@GetMapping(value = "/format", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ListDto<TopicBriefOutputDto> listTopicsSimples() {
		List<Topic> lista = this.topicRepository.findAll();
		List<TopicBriefOutputDto> listaDto = TopicBriefOutputDto.listFromTopics(lista);
		return new ListDto<TopicBriefOutputDto>(listaDto);
	}

	@Cacheable(value = "topicById", key="#id")
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public TopicByIdOutputDto topicById(@PathVariable("id") Long id) {
		Topic topic= this.topicRepository.getOne(id);
		TopicByIdOutputDto dto= TopicByIdOutputDto.fromTopic(topic);
		return dto;
	}
	
	
//	@Secured("ROLE_USER")
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public Page<TopicBriefOutputDto> topicBriefOutput(TopicSearchInputDto topicSearchInput,
			@PageableDefault(sort = "creationInstant", direction = Sort.Direction.DESC) Pageable pageRequest) {

		Specification<Topic> spec = topicSearchInput.build();
		Page<Topic> page = this.topicRepository.findAll(spec, pageRequest);
		return TopicBriefOutputDto.pageFromTopics(page);
	}
	
	@CacheEvict(value = "dashboard", key="1")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TopicOutputDto> createTopic(@RequestBody @Valid NewTopicInputDto newTopicDto,
			@AuthenticationPrincipal User loggedUser, UriComponentsBuilder uriBuilder) {

		Topic topic = newTopicDto.build(loggedUser, this.courseRepository);
		Topic persistido = this.topicRepository.save(topic);

		URI path = uriBuilder.path("/api/topics/{id}").buildAndExpand(topic.getId()).toUri();

		return ResponseEntity.created(path).body(new TopicOutputDto(persistido));
	}
	
	@Cacheable(value = "dashboard", key="1")
	@GetMapping(value="/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DashboardDto> dashboard() {
		
		List<Category> categories = this.categoryRepository.findByCategoryIsNull();
		List<DashboardDto> dtos = categories.stream().map(c -> {
			
//			Instant lastWeek = Instant.now().minus(7, ChronoUnit.DAYS);

			List<Category> subCats = this.categoryRepository.findByCategory(c);
			Integer count = this.topicRepository.countByCourseSubcategoryCategory(c);
			
			DashboardDto dto1 = new DashboardDto();
			dto1.setCategoryName(c.getName());
			dto1.setSubcategories(subCats);
			dto1.setAllTopics(count);
			return dto1;
		}).collect(Collectors.toList());
		
		return dtos;
	}
}
