package br.com.caelum.forum.topic.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.caelum.forum.model.Answer;
import br.com.caelum.forum.model.Topic;

public class TopicByIdOutputDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private long id;
	private String shortDescription;// Descrição curta do tópico,
	private String content;// Conteúdo detalhado do tópico,
	private String status;// NOT_ANSWERED, // status do tópico
    private Instant creationInstant;// 2018-01-01T12;//00;//00.000Z, // instante em que foi criado
    private Instant lastUpdate;// 2018-01-01T12;//00;//00.000Z, // instante da última atualização
    
    private String courseName;// Nome do curso ao qual o tópico é relacionado,
    private String subcategoryName;// Subcategoria do curso,
    private String categoryName;// Categoria do curso,
    private String ownerName;// Nome do usuário autor do tópico,
    
    private int numberOfResponses;// 0, // numero de respostas
    private List<AnswerOutputDto> answers;// [ // lista com todas as respostas
    
    
	public TopicByIdOutputDto(long id, 
			String shortDescription, 
			String content, 
			String status, 
			Instant creationInstant,
			Instant lastUpdate, 
			String courseName, 
			String subcategoryName, 
			String categoryName, 
			String ownerName,
			int numberOfResponses, 
			List<AnswerOutputDto> answers) {
		this.id = id;
		this.shortDescription = shortDescription;
		this.content = content;
		this.status = status;
		this.creationInstant = creationInstant;
		this.lastUpdate = lastUpdate;
		this.courseName = courseName;
		this.subcategoryName = subcategoryName;
		this.categoryName = categoryName;
		this.ownerName = ownerName;
		this.numberOfResponses = numberOfResponses;
		this.answers = answers;
	}
	public long getId() {
		return id;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public String getContent() {
		return content;
	}
	public String getStatus() {
		return status;
	}
	public Instant getCreationInstant() {
		return creationInstant;
	}
	public Instant getLastUpdate() {
		return lastUpdate;
	}
	public String getCourseName() {
		return courseName;
	}
	public String getSubcategoryName() {
		return subcategoryName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public int getNumberOfResponses() {
		return numberOfResponses;
	}
	public List<AnswerOutputDto> getAnswers() {
		return answers;
	}
	public static TopicByIdOutputDto fromTopic(Topic topic) {
		
		List<Answer> answers = topic.getAnswers();
		List<AnswerOutputDto> answerDtoList = new ArrayList<>();
		
		answers.stream().map(AnswerOutputDto::new).collect(Collectors.toList());

		
		TopicByIdOutputDto dto = new TopicByIdOutputDto(
				topic.getId(), 
				topic.getShortDescription(), 
				topic.getContent(), 
				topic.getStatus().name(), 
				topic.getCreationInstant(), 
				topic.getLastUpdate(), 
				topic.getCourse().getName(), 
				topic.getCourse().getSubcategoryName(),
				topic.getCourse().getCategoryName(), 
				topic.getOwner().getName(), 
				answers.size(),
				answerDtoList);
		
		return dto;
	}

    
    
    
}
