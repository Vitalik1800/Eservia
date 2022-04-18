package com.eservia.booking.di.modules;

import android.content.Context;

import com.eservia.booking.util.NotificationController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NotificationControllerModule {

    @Provides
    @Singleton
    public NotificationController provideNotificationController(Context context) {
        return new NotificationController(context);
    }
}
