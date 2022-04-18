package com.eservia.booking.di;

import com.eservia.booking.App;
import com.eservia.booking.di.modules.ApiModule;
import com.eservia.booking.di.modules.ContextModule;
import com.eservia.booking.di.modules.NetworkStateProviderModule;
import com.eservia.booking.di.modules.NotificationControllerModule;
import com.eservia.booking.di.modules.ObjectBoxModule;
import com.eservia.booking.di.modules.RetrofitRestClientModule;

public class DaggerManager {

    private AppComponent mAppComponent;

    public void initNewAppComponent(App app) {
        mAppComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(app))
                .objectBoxModule(new ObjectBoxModule())
                .networkStateProviderModule(new NetworkStateProviderModule())
                .retrofitRestClientModule(new RetrofitRestClientModule())
                .apiModule(new ApiModule())
                .notificationControllerModule(new NotificationControllerModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
