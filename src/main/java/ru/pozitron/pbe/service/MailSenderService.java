package ru.pozitron.pbe.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailSenderService {

    private final JavaMailSender emailSender;


    @Value("${spring.mail.username}")
    private String username;

    public MailSenderService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }


    public void sendMimeMessage(String emailTo,String subject,String message) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true,"UTF-8");
        
        mimeMessageHelper.setFrom(username);
        mimeMessageHelper.setTo(emailTo);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(message,true);
        emailSender.send(mimeMessage);
    }
}
