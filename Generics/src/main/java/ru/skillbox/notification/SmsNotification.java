package ru.skillbox.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class SmsNotification implements Notification {
    @Getter
    private List<String> receivers;
    private String message;

    @Override
    public String formattedMessage() {
        return message;
    }
}
