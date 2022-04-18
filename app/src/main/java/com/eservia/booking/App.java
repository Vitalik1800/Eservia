package com.eservia.booking;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.eservia.biv.BigImageViewer;
import com.eservia.booking.di.AppComponent;
import com.eservia.booking.di.DaggerManager;
import com.eservia.glide.GlideImageLoader;
import com.eservia.model.prefs.common.PreferencesManager;
import com.eservia.utils.LocaleUtil;

public class App extends Application {

    public static App appInstance;
    private DaggerManager mDaggerManager;

    public static App getInstance() {
        return appInstance;
    }

    public static AppComponent getAppComponent() {
        return appInstance
                .getDaggerManager()
                .getAppComponent();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        mDaggerManager = new DaggerManager();
        mDaggerManager.initNewAppComponent(this);
        PreferencesManager.initializeInstance(this);
        try {
            BigImageViewer.initialize(GlideImageLoader.with(this));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleUtil.setLocale(base));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleUtil.setLocale(this);
    }

    public DaggerManager getDaggerManager() {
        return mDaggerManager;
    }
}
