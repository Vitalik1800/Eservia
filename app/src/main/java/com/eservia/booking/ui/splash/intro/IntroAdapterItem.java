package com.eservia.booking.ui.splash.intro;

import android.graphics.drawable.Drawable;

public class IntroAdapterItem {

    private Drawable image;

    private String message;

    public IntroAdapterItem() {
    }

    public IntroAdapterItem(Drawable image, String message) {
        this.image = image;
        this.message = message;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
