package com.server.token.service;

public interface EmailService {
    void sendEmail(String userEmail,String title,String key);
}
