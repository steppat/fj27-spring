package br.com.caelum.forum.topic.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.caelum.forum.model.Course;
import br.com.caelum.forum.model.Topic;
import br.com.caelum.forum.model.User;
import br.com.caelum.forum.repository.CourseRepository;

public class NewTopicInputDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank
    @Size(min = 10)
    private String shortDescription;

	@NotBlank
    @Size(min = 10)
    private String content;
	
	@NotEmpty
    private String courseName;
    
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
    public Topic build(User owner, CourseRepository courseRepository) {
        
        Course course = courseRepository.findByNameStartsWith(this.courseName);
        return new Topic(this.shortDescription, this.content, owner, course);
    }
}