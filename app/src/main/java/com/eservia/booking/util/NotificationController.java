package com.eservia.booking.util;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.eservia.booking.R;

import java.util.Random;

public class NotificationController {

    private static final String BOOKING_CHANNEL_ID = "id_channel_eservia_booking";

    private static final String MARKETING_CHANNEL_ID = "id_channel_eservia_marketing";

    private final Context context;

    private NotificationManager notificationManager;

    public NotificationController(Context context) {
        this.context = context;
        initNotificationManager();
    }

    public void showBookingNotification(String message, PendingIntent intent) {
        notifyBooking(
                message,
                intent,
                getBigTextStyle(message),
                getRandomId());
    }

    public void showMarketingNotification(String message, PendingIntent intent) {
        notifyMarketing(
                message,
                intent,
                getBigTextStyle(message),
                getRandomId());
    }

    private void notifyBooking(String message, PendingIntent pendingIntent,
                               NotificationCompat.Style style, int id) {

        Notification notification = buildNotification(
                message,
                pendingIntent,
                style,
                BOOKING_CHANNEL_ID);

        notificationManager.notify(id, notification);
    }

    private void notifyMarketing(String message, PendingIntent pendingIntent,
                                 NotificationCompat.Style style, int id) {

        Notification notification = buildNotification(
                message,
                pendingIntent,
                style,
                MARKETING_CHANNEL_ID);

        notificationManager.notify(id, notification);
    }

    private void initNotificationManager() {
        notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channelBooking = getBookingChanel();
            NotificationChannel channelMarketing = getMarketingChanel();

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channelBooking);
                notificationManager.createNotificationChannel(channelMarketing);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private NotificationChannel getBookingChanel() {
        NotificationChannel channel = new NotificationChannel(BOOKING_CHANNEL_ID,
                getBookingChannelName(),
                NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        return channel;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private NotificationChannel getMarketingChanel() {
        NotificationChannel channel = new NotificationChannel(MARKETING_CHANNEL_ID,
                getMarketingChannelName(),
                NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        return channel;
    }

    private Notification buildNotification(String text,
                                           PendingIntent pendingIntent,
                                           NotificationCompat.Style style,
                                           String channelId) {
        return new NotificationCompat.Builder(context, channelId)
                .setContentTitle(null)
                .setContentText(text)
                .setSmallIcon(R.drawable.icon_notification_small)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setLargeIcon(null)
                .setContentIntent(pendingIntent)
                .setStyle(style)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{600, 800})
                .setAutoCancel(true)
                .build();
    }

    private NotificationCompat.Style getBigTextStyle(String message) {
        return new NotificationCompat.BigTextStyle().bigText(message);
    }

    private int getRandomId() {
        return new Random().nextInt();
    }

    private String getBookingChannelName() {
        return context.getResources().getString(R.string.booking);
    }

    private String getMarketingChannelName() {
        return context.getResources().getString(R.string.news_and_promotions);
    }

}
