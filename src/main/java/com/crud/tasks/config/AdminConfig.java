package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AdminConfig {
    @Value("${spring.mail.username}")
    private String adminMail;

    @Value("${admin.name}")
    private String adminName;
}
