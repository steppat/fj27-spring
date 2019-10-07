package br.com.caelum.forum.topic.dto;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.domain.Page;

import br.com.caelum.forum.model.Topic;
import br.com.caelum.forum.model.TopicStatus;

@XmlRootElement(name="topic") 
public class TopicBriefOutputDto {

//	https://www.baeldung.com/jackson-annotations
//	@JsonProperty("pk")
	private Long id;
	private String shortDescription;
	private long secondsSinceLastUpdate;
	private String ownerName;
	private String courseName;
	private String subcategoryName;
	private String categoryName;
	private int numberOfResponses;
	private boolean solved;

	@Deprecated
	public TopicBriefOutputDto() {
	}

	public TopicBriefOutputDto(Topic topic) {
		this.id = topic.getId();
		this.shortDescription = topic.getShortDescription();
		this.secondsSinceLastUpdate = getSecondsSince(topic.getLastUpdate());
		this.ownerName = topic.getOwner().getName(); 
		this.courseName = topic.getCourse().getName();
		this.subcategoryName = topic.getCourse().getSubcategory().getName();
		this.categoryName = topic.getCourse().getCategoryName();
		this.numberOfResponses = topic.getNumberOfAnswers();
		this.solved = TopicStatus.SOLVED.equals(topic.getStatus());
	}

	public static List<TopicBriefOutputDto> listFromTopics(List<Topic> topics) {
		return topics.stream().map(TopicBriefOutputDto::new).collect(Collectors.toList());
	}
	
	public static Page<TopicBriefOutputDto> pageFromTopics(Page<Topic> topics) {
		return topics.map(TopicBriefOutputDto::new);
	}


	private long getSecondsSince(Instant lastUpdate) {
		return Duration.between(lastUpdate, Instant.now()).get(ChronoUnit.SECONDS);
	}

	
	public void setId(Long id) {
		this.id = id;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public void setSecondsSinceLastUpdate(long secondsSinceLastUpdate) {
		this.secondsSinceLastUpdate = secondsSinceLastUpdate;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setSubcategoryName(String subcategoryName) {
		this.subcategoryName = subcategoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setNumberOfResponses(int numberOfResponses) {
		this.numberOfResponses = numberOfResponses;
	}

	public void setSolved(boolean solved) {
		this.solved = solved;
	}

//	@JsonGetter("pkGetter")
	public Long getId() {
		return id;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public long getSecondsSinceLastUpdate() {
		return secondsSinceLastUpdate;
	}

	public String getOwnerName() {
		return ownerName;
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

	public int getNumberOfResponses() {
		return numberOfResponses;
	}

	public boolean isSolved() {
		return solved;
	}

}
