package org.mail.maildemonew.service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.mail.maildemonew.model.EmailConfig;
import org.mail.maildemonew.repository.EmailConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private EmailConfigRepository emailConfigRepository;

    public void sendEmail(String to, String subject, String body) {
        EmailConfig config = emailConfigRepository.findByIsActiveTrue();
        if (config == null) {
            throw new RuntimeException("No active email configuration found");
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", config.isAuth());
        props.put("mail.smtp.starttls.enable", config.isTlsEnable());
        props.put("mail.smtp.host", config.getHost());
        props.put("mail.smtp.port", config.getPort());

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getUsername(), config.getPassword());
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(config.getUsername()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            log.info("Email sent successfully to: {}", to);
        } catch (MessagingException e) {
            log.error("Failed to send email", e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}