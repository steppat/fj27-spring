package br.com.caelum.forum.answer.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterNewAnswerInputDto {

	@NotBlank
	@Size(min = 5)
	private String content;

	public RegisterNewAnswerInputDto() {
	}

	public RegisterNewAnswerInputDto(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

}
