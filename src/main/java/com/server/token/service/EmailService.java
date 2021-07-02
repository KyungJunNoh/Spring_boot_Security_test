package com.server.token.service;

public interface EmailService {
    void sandEmail(String userEmail,String title,String text);
}
