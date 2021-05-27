package com.requirementsphase.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class MailService  {

    @Autowired
    private JavaMailSender javaMailSender;

  public void sendEmail(String sub, String body, String[] list) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(list);
        msg.setSubject(sub);
        msg.setText(body);
        javaMailSender.send(msg);

    }

   public void sendEmailWithAttachment(String sub, String body, String[] list) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        for(String mailAddress:list)
        {
        	 helper.setTo(mailAddress);
             helper.setSubject(sub);
             helper.setText(body, true);
             helper.addAttachment("my_photo.png", new ClassPathResource("demoPic.png"));
             javaMailSender.send(msg);

        	
        }
       
    }
}

