package com.server.token.service.Impl;

import com.server.token.service.EmailService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    @Override
    public void sandEmail() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("hippo911911@naver.com");
        simpleMailMessage.setSubject("Spring Test");
        simpleMailMessage.setText("hello world");
        javaMailSender.send(simpleMailMessage);
    }
}
