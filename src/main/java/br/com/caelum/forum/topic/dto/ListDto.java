package br.com.caelum.forum.topic.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "list")
@XmlSeeAlso(TopicBriefOutputDto.class) 
public class ListDto<T> {
	
	private List<T> elementos;

	public ListDto() {
	}

	public ListDto(List<T> elementos) {
		this.elementos = elementos;
	}
	
	@XmlElement(name="topic")
	public List<T> getList() {
		return elementos;
	}

}
