package br.com.caelum.forum.topic.dto;

public class TopicInputDto {

	private String shortDescription;
	private String content;
	private Long ownerId;
	private Long courseId;

	public String getShortDescription() {
		return shortDescription;
	}

	public String getContent() {
		return content;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public Long getCourseId() {
		return courseId;
	}
}
