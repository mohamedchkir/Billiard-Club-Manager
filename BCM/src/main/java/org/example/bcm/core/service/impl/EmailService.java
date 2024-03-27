package org.example.bcm.core.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmailService {


    private final JavaMailSender mailSender;

    public void sendResetLinkPassword(String to, String resetLink) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            message.setFrom(new InternetAddress("mohamedchkir2@gmail.com"));
            message.setRecipients(MimeMessage.RecipientType.TO, to);
            message.setSubject("BCM - Reset Password");

            String htmlContent = "<html><head><style>"
                    + "body {font-family: Arial, sans-serif;}"
                    + ".container {max-width: 600px; margin: auto; padding: 20px;}"
                    + ".header {background-color: #f8f9fa; padding: 20px; text-align: center;}"
                    + ".content {background-color: #ffffff; padding: 20px;}"
                    + ".footer {background-color: #f8f9fa; padding: 10px; text-align: center;}"
                    + "</style></head><body>"
                    + "<div class='container'>"
                    + "<div class='header'>"
                    + "<h2>Reset Your Password</h2>"
                    + "</div>"
                    + "<div class='content'>"
                    + "<p>You have requested to reset your password. Click the link below to reset it:</p>"
                    + "<a href='" + resetLink + "'>Reset Password : </a>" +
                    "<span>" + resetLink + "</span>"
                    + "<p>If you did not request this, please ignore this email.</p>"
                    + "</div>"
                    + "<div class='footer'>"
                    + "<p>Copyright © 2024 BCM. All rights reserved.</p>"
                    + "</div>"
                    + "</div></body></html>";
            message.setContent(htmlContent, "text/html; charset=utf-8");

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
