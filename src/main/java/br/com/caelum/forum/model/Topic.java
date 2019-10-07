package br.com.caelum.forum.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.util.Assert;

@Entity
public class Topic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String shortDescription;
	@Lob
	private String content;
	private Instant creationInstant = Instant.now();
	private Instant lastUpdate = Instant.now();
	@Enumerated(EnumType.STRING)
	private TopicStatus status = TopicStatus.NOT_ANSWERED;
	@ManyToOne
	private User owner;
	@ManyToOne
	private Course course;
	@OneToMany(mappedBy = "topic")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private List<Answer> answers = new ArrayList<>();

	public Topic() {
		
	}

	public Topic(String shortDescription, String content, User owner, Course course) {
		super();
		this.shortDescription = shortDescription;
		this.content = content;
		this.owner = owner;
		this.course = course;
	}
	// getters

	public Long getId() {
		return id;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public String getContent() {
		return content;
	}

	public Instant getCreationInstant() {
		return creationInstant;
	}

	public Instant getLastUpdate() {
		return lastUpdate;
	}

	public TopicStatus getStatus() {
		return status;
	}

	public User getOwner() {
		return owner;
	}

	public Course getCourse() {
		return course;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public int getNumberOfAnswers() {
		return this.answers.size();
	}
	
	void setStatus(TopicStatus status) {
		this.status = status;
	}
	
	void addAnswer(Answer answer) {
		this.answers.add(answer);
	}


	
	public void setId(Long id) {
		this.id = id;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreationInstant(Instant creationInstant) {
		this.creationInstant = creationInstant;
	}

	public void setLastUpdate(Instant lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public void registerNewReply(Answer newReply) {
		Assert.notNull(newReply, "Nova resposta não pode ser nula");

		this.status.registerNewReply(this, newReply);
	}

	public void markAsSolved(Answer solution) {
		Assert.notNull(solution, "A resposta de solução não pode ser nula");

		this.status.markAsSolved(this);
	}

	public void close() {
		this.status.close(this);
	}

	public String getOwnerEmail() {
		return this.owner.getUsername();
	}

	public String getOwnerName() {
		return this.owner.getName();
	}

	
	
}
