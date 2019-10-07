package br.com.caelum.forum.model;

public interface TopicState {

	void registerNewReply(Topic topic, Answer newReply);

	void markAsSolved(Topic topic);

	void close(Topic topic);

}
