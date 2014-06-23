package com.andreaseisele.sample.tx;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author <a href="mailto:ae@andreaseisele.com">Andreas Eisele</a>
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

    @Value("${email.host:localhost}")
    private String host;

    // note: using SMTP port > 1024 for testing purposes...
    @Value("${email.port:2500}")
    private Integer port;

    @Value("${email.from:example@andreaseisele.com}")
    private String from;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(host);
        javaMailSender.setPort(port);

        Properties mailProperties = new Properties();
        mailProperties.setProperty("mail.transport.protocol", "smtp");
        mailProperties.setProperty("mail.smtp.auth", "false");
        javaMailSender.setJavaMailProperties(mailProperties);

        return javaMailSender;
    }

    @Bean
    public SimpleMailMessage templateMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        return message;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
