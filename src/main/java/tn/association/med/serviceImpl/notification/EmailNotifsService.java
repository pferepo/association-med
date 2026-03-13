package tn.association.med.serviceImpl.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotifsService {

    @Autowired
    private JavaMailSender mailSender;

    public void envoyerEmail(String destinataire, String sujet, String message) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(destinataire);
        mail.setSubject(sujet);
        mail.setText(message);

        mailSender.send(mail);
    }
}