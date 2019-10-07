package br.com.caelum.forum.answer.dto;

import java.time.Instant;

import br.com.caelum.forum.model.Answer;

public class RegisterNewAnswerOutputDto {

	private Instant creationTime;
	private String content;
	private Long id;
	private String ownerName;
	private boolean solution;

	public RegisterNewAnswerOutputDto(Answer answer) {

		this.id = answer.getId();
		this.content = answer.getContent();
		this.creationTime = answer.getCreationTime();
		this.ownerName = answer.getOwnerName();
		this.solution = answer.isSolution();
	}

	public Instant getCreationTime() {
		return creationTime;
	}

	public String getContent() {
		return content;
	}

	public Long getId() {
		return id;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public boolean isSolution() {
		return solution;
	}
	
	

}
