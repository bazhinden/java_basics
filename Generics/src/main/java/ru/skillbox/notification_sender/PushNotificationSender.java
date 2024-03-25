package ru.skillbox.notification_sender;

import ru.skillbox.notification.PushNotification;

import java.util.List;

public class PushNotificationSender implements NotificationSender<PushNotification> {
    @Override
    public void send(PushNotification notification) {
        System.out.println("PUSH");
        System.out.println("title: " + notification.getTitle());
        System.out.println("receiver: " + notification.getReceiver());
        System.out.println("message: " + notification.formattedMessage());
        System.out.println();
    }

    @Override
    public void send(List<PushNotification> notifications) {
        for (PushNotification notification : notifications) {
            send(notification);
        }
    }
}