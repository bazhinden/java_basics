package ru.skillbox.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class EmailNotification implements Notification {
    @Getter
    private String subject;
    @Getter
    private List<String> receivers;
    private String message;

    @Override
    public String formattedMessage() {
        return "<p>" + message + "</p>";
    }
}
