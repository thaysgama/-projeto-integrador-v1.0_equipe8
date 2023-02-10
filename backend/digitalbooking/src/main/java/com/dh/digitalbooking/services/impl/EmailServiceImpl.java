package com.dh.digitalbooking.services.impl;

import com.dh.digitalbooking.services.IEmailSender;
import lombok.AllArgsConstructor;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements IEmailSender {
    private final static Logger log = Logger.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;


    @Override
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("DB | Confirme seu email");
            helper.setFrom("welcome.digitalbooking@gmail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Failed to send email.", e);
            throw new IllegalStateException("Failed to send email.");
        }
    }
}
