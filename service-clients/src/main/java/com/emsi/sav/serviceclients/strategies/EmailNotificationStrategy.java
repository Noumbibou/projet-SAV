package com.emsi.sav.serviceclients.strategies;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationStrategy implements NotificationStrategy{

    private final JavaMailSender mailSender;

    public EmailNotificationStrategy(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(String destinataire, String sujet, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(destinataire);
        mailMessage.setSubject(sujet);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
}
