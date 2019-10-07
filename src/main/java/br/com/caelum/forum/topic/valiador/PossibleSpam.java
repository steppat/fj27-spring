package br.com.caelum.forum.topic.valiador;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import br.com.caelum.forum.model.Topic;

public class PossibleSpam {

    private List<Topic> topics;

    public PossibleSpam(List<Topic> topics) {
        this.topics = topics;
    }

    public boolean hasTopicLimitExceeded() {
        return this.topics.size() >= 10;
    }

    public long minutesToNextTopic(Instant from) {
        Instant instantOfTheOldestTopic = topics.get(0).getCreationInstant();

        return Duration.between(from, instantOfTheOldestTopic)
                .getSeconds() / 60;
    }
}

