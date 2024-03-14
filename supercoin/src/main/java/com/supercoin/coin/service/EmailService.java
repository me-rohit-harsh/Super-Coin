package com.supercoin.coin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	
	@Autowired	
    private final JavaMailSender javaMailSender;
	public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
	@Async
	 public void sendEmail(String to, String subject, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // Enable HTML content
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle exception properly
        }
    }
}
