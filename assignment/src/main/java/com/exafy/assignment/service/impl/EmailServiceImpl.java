package com.exafy.assignment.service.impl;

import com.exafy.assignment.exception.BadRequestException;
import com.exafy.assignment.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {


    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String sendTo, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendTo);
        message.setSubject(subject);
        message.setText(body);

        try {
         mailSender.send(message);
        } catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
