package com.exafy.assignment.service;

public interface EmailService {

    void sendEmail(String sendTo, String subject, String body);
}
