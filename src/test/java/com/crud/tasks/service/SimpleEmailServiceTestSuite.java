package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SimpleEmailServiceTestSuite {

    @InjectMocks
    SimpleEmailService simpleEmailService;

    @Mock
    JavaMailSender javaMailSender;

    private Mail mail;

    @Test
    void shouldSendEmail() {
        //Given
        mail = Mail.builder()
                .mailTo("test1@test.com")
                .subject("Test")
                .message("Test Message")
                .toCc("test2@test.com")
                .build();

        SimpleMailMessage mailMessage = getSimpleMailMessage();

        //When
        simpleEmailService.send(mail);

        //Then
        verify(javaMailSender, times(1)).send(mailMessage);
    }

    @Test
    void shouldSendEmailWithoutToCc() {
        //Given
        mail = Mail.builder()
                .mailTo("test1@test.com")
                .subject("Test")
                .message("Test Message")
                .build();

        SimpleMailMessage mailMessage = getSimpleMailMessage();

        //When
        simpleEmailService.send(mail);

        //Then
        verify(javaMailSender, times(1)).send(mailMessage);
    }

    private SimpleMailMessage getSimpleMailMessage() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        Optional.ofNullable(mail.getToCc()).ifPresent(mailMessage::setCc);
        return mailMessage;
    }
}