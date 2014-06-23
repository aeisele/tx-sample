package com.andreaseisele.sample.tx.service;

import com.andreaseisele.sample.tx.domain.User;
import com.andreaseisele.sample.tx.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author <a href="mailto:ae@andreaseisele.com">Andreas Eisele</a>
 */
@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SimpleMailMessage templateMessage;

    @Transactional
    public User register(String email, String password) {
        // TODO should validate arguments
        User user = new User(email, password);
        user = repository.save(user);

        sendWelcomeMail(email);

        return user;
    }

    private void sendWelcomeMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage(templateMessage);
        message.setTo(email);
        // TODO i18n
        message.setText("welcome new user");

        // sometimes it nay be better to fail instead of just logging the error
        try {
            mailSender.send(message);
        } catch (MailException me) {
            logger.error("error sending registration mail", me);
        }
    }

}
