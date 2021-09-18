package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
public class MailCreatorService {

    public static final String TEMPLATE_CREATE_TRELLO_TASK = "mail/created-trello-card-mail";
    public static final String TEMPLATE_NUMBER_OF_TASK = "mail/number-of-tasks-in-db";

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    private final List<String> functionality = List.of(
            "You can manage yours tasks",
            "Provides connection with Trello Account",
            "Application allows sending tasks to Trello"
    );

    public String buildEmail(String message, String template) {
        Context context = createContext(message);
        return templateEngine.process(template, context);
    }

    private Context createContext(String message) {
        Context context = new Context();
        context.setVariable("preview_message", "The message was generated automatically - do not reply.");
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://127.0.0.1:5500/");
        context.setVariable("button", "Visit website");
        context.setVariable("show_button", true);
        context.setVariable("app_functionality", functionality);
        context.setVariable("regards_part1", "Best regards, ");
        context.setVariable("regards_part2", "CRUD support team.");
        context.setVariable("company_name", adminConfig.getCompanyName());
        context.setVariable("company_goal", adminConfig.getCompanyGoal());
        context.setVariable("company_email", adminConfig.getCompanyEmail());
        context.setVariable("company_phone", adminConfig.getCompanyPhone());
        return context;
    }
}
