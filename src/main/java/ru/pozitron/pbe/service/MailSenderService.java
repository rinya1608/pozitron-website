package ru.pozitron.pbe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender emailSender;


    @Value("${spring.mail.username}")
    private String username;


    public void sendMimeMessage(String emailTo,String subject,String message) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(username);
        mimeMessageHelper.setTo(emailTo);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(message);
        emailSender.send(mimeMessage);
    }
}
