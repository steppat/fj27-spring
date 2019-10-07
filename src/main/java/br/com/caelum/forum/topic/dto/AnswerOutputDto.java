package br.com.caelum.forum.topic.dto;

import java.io.Serializable;
import java.time.Instant;

import br.com.caelum.forum.model.Answer;

public class AnswerOutputDto  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private long id;// 100,
    private String content;// Conteúdo da resposta,
    private Instant creationTime;// 2018-01-01T12;//00;//00.000Z,
    private boolean solution;// false, // resposta é a solução para o tópico ?
    private String ownerName;// Nome do Usuário autor da resposta


    public AnswerOutputDto() {
	}
    
    public AnswerOutputDto(Answer a) {
		this.id = a.getId();
		this.content = a.getContent();
		this.creationTime = a.getCreationTime();
		this.solution = a.isSolution();
		this.ownerName = a.getOwnerName();
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public Instant getCreationTime() {
		return creationTime;
	}

	public boolean isSolution() {
		return solution;
	}

	public String getOwnerName() {
		return ownerName;
	}
    
}
