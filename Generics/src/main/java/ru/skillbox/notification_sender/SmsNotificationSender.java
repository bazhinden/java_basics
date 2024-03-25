package ru.skillbox.notification_sender;

import ru.skillbox.notification.SmsNotification;

import java.util.List;

public class SmsNotificationSender implements NotificationSender<SmsNotification> {
    @Override
    public void send(SmsNotification notification) {
        System.out.println("SMS");
        System.out.println("receivers: " + notification.getReceivers());
        System.out.println("message: " + notification.formattedMessage());
        System.out.println();
    }

    @Override
    public void send(List<SmsNotification> notifications) {
        for (SmsNotification notification : notifications) {
            send(notification);
        }
    }
}
