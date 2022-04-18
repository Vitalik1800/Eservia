package com.eservia.booking.di.modules;

import android.content.Context;

import com.eservia.model.entity.MyObjectBox;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.objectbox.BoxStore;

@Module
public class ObjectBoxModule {

    @Provides
    @Singleton
    public BoxStore provideAppDatabase(Context context) {
        return MyObjectBox.builder().androidContext(context).build();
    }
}
