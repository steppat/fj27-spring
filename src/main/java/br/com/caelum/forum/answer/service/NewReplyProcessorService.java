package br.com.caelum.forum.answer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.caelum.forum.answer.mail.ForumMailService;
import br.com.caelum.forum.model.Answer;
import br.com.caelum.forum.repository.AnswerRepository;

@Service
public class NewReplyProcessorService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ForumMailService forumMailService;

    public void execute(Answer answer) {
        this.answerRepository.save(answer);
        this.forumMailService.sendNewReplyMail(answer);
    }
}

