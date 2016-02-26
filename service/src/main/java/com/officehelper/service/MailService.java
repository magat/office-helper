package com.officehelper.service;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private MailSender mailSender;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String toAddress, String fromAddress, String subject, String msgBody) {
        SimpleMailMessage templateMessage = new SimpleMailMessage();
        templateMessage.setFrom(fromAddress);
        templateMessage.setTo(toAddress);
        templateMessage.setSubject(subject);
        templateMessage.setText(msgBody);
        mailSender.send(templateMessage);
    }
}
