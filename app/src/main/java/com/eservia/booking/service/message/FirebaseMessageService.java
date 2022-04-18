package com.eservia.booking.service.message;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;

import com.eservia.booking.App;
import com.eservia.booking.ui.home.HomeActivity;
import com.eservia.booking.util.NotificationController;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import javax.inject.Inject;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class FirebaseMessageService extends FirebaseMessagingService {

    @Inject
    NotificationController mNotificationController;

    @Inject
    Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        App.getAppComponent().inject(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        showNotification(remoteMessage);
    }

    private void showNotification(RemoteMessage remoteMessage) {

        String mMessageType = "messageType";
        String type = remoteMessage.getData().get(mMessageType);

        String mMessage = "message";
        String message = remoteMessage.getData().get(mMessage);

        String mTitle = "title";
        String title = remoteMessage.getData().get(mTitle);

        if (type == null) {
            return;
        }

        if (isBookingMessage(type) && title != null) {
            mNotificationController.showBookingNotification(title,
                    getHomeActivityBookingsPendingIntent(mContext));
        } else if (title != null) {
            mNotificationController.showMarketingNotification(title,
                    getHomeActivityMarketingPendingIntent(mContext));
        }
    }

    private boolean isBookingMessage(String type) {
        return type.toLowerCase().startsWith(("BOOKING_").toLowerCase());
    }

    private static PendingIntent getHomeActivityBookingsPendingIntent(Context context) {
        TaskStackBuilder builder = TaskStackBuilder.create(context);
        builder.addParentStack(HomeActivity.class);
        builder.addNextIntent(HomeActivity.init(context, HomeActivity.KEY_TAB_BOOKINGS));
        return builder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static PendingIntent getHomeActivityMarketingPendingIntent(Context context) {
        TaskStackBuilder builder = TaskStackBuilder.create(context);
        builder.addParentStack(HomeActivity.class);
        builder.addNextIntent(HomeActivity.init(context, HomeActivity.KEY_TAB_MARKETING));
        return builder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
