package ru.skillbox;

import ru.skillbox.notification.EmailNotification;
import ru.skillbox.notification.PushNotification;
import ru.skillbox.notification.SmsNotification;
import ru.skillbox.notification_sender.EmailNotificationSender;
import ru.skillbox.notification_sender.PushNotificationSender;
import ru.skillbox.notification_sender.SmsNotificationSender;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EmailNotification emailNotification1 = new EmailNotification("Успешная регистрация!",
                List.of("oleg@java.skillbox.ru", "masha@java.skillbox.ru", "yan@java.skillbox.ru"),
                "Спасибо за регистрацию на сервисе!");

        SmsNotification smsNotification1 = new SmsNotification(List.of("+70001234567"),
                "Спасибо за регистрацию на сервисе!");

        PushNotification pushNotification1 = new PushNotification("Успешная регистрация!",
                "o.yanovich", "Спасибо за регистрацию на сервисе!");

        EmailNotificationSender emailSender = new EmailNotificationSender();
        SmsNotificationSender smsSender = new SmsNotificationSender();
        PushNotificationSender pushSender = new PushNotificationSender();

        emailSender.send(emailNotification1);
        smsSender.send(smsNotification1);
        pushSender.send(pushNotification1);
    }
}

