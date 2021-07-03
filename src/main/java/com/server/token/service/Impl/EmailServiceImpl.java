package com.server.token.service.Impl;

import com.server.token.domain.dto.UserEmailDto;
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
    public void sendEmail(String userEmail, String title, String key) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject(title);
        message.setText("Spring boot에서 보낸 인증 키 : " + key);
        javaMailSender.send(message);
    }
}
