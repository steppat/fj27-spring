package br.com.caelum.forum.answer.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.caelum.forum.model.Answer;
import br.com.caelum.forum.model.Topic;

@Service
public class ForumMailService {
    
    private static final Logger logger = LoggerFactory
        .getLogger(ForumMailService.class);

//    @Autowired
//    private MailSender mailSender;

    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private NewReplyMailFactory mailFactory;
    
    @Async
    public void sendNewReplyMail(Answer answer) {
        Topic answeredTopic = answer.getTopic();
        
        MimeMessagePreparator messagePreparator = (mimeMessage) -> {
        	MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        	messageHelper.setTo(answeredTopic.getOwnerEmail());
        	messageHelper.setSubject("Novo comentário em: " + answeredTopic.getShortDescription());
        	
        	messageHelper.setText(this.mailFactory.generateNewReplyMailContent(answer),true);
        	
        };

//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(answeredTopic.getOwnerEmail());
//        message.setSubject("Novo comentário em: " + answeredTopic.getShortDescription());
//
//        message.setText("Olá " + answeredTopic.getOwnerEmail() + "\n\n" +
//                "Há uma nova mensagem no fórum! " + answer.getOwnerName() +
//                " comentou no tópico: " + answeredTopic.getShortDescription());

        try {
            mailSender.send(messagePreparator);
            logger.info("Email enviado para " + answeredTopic.getOwnerEmail());

        } catch (MailException e) {
            logger.error("Não foi possível enviar email para " + answer.getTopic()
                .getOwnerEmail(), e.getMessage());
        }
    }
}

