package com.husseinelbhrawy.MovieAPI.Auth.Services.Implementation;

import com.husseinelbhrawy.MovieAPI.Auth.DTO.MailBody;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServices {

    @Value("${spring.mail.username}")
    private  String username;

    private  final JavaMailSender javaMailSender;

    public  void sendSimpleEmail(MailBody body) throws MessagingException {
        System.out.println("Send Simple Email to  " +body.to());
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(body.to());
        message.setFrom(username); //! Get From Application.yml
        message.setSubject(body.subject());
        message.setText(body.body());

        javaMailSender.send(message);

    }




}
