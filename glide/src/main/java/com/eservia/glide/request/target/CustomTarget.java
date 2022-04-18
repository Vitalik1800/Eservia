package com.eservia.glide.request.target;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eservia.glide.request.Request;
import com.eservia.glide.util.Util;


public abstract class CustomTarget<T> implements Target<T> {

  private final int width;
  private final int height;

  @Nullable private Request request;


  public CustomTarget() {
    this(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
  }

  public CustomTarget(int width, int height) {
    if (!Util.isValidDimensions(width, height)) {
      throw new IllegalArgumentException(
          "Width and height must both be > 0 or Target#SIZE_ORIGINAL, but given"
              + " width: "
              + width
              + " and height: "
              + height);
    }

    this.width = width;
    this.height = height;
  }

  @Override
  public void onStart() {
    // Intentionally empty, this can be optionally implemented by subclasses.
  }

  @Override
  public void onStop() {
    // Intentionally empty, this can be optionally implemented by subclasses.
  }

  @Override
  public void onDestroy() {
    // Intentionally empty, this can be optionally implemented by subclasses.
  }

  @Override
  public void onLoadStarted(@Nullable Drawable placeholder) {
    // Intentionally empty, this can be optionally implemented by subclasses.
  }

  @Override
  public void onLoadFailed(@Nullable Drawable errorDrawable) {
    // Intentionally empty, this can be optionally implemented by subclasses.
  }

  @Override
  public final void getSize(@NonNull SizeReadyCallback cb) {
    cb.onSizeReady(width, height);
  }

  @Override
  public final void removeCallback(@NonNull SizeReadyCallback cb) {
    // Do nothing, this class does not retain SizeReadyCallbacks.
  }

  @Override
  public final void setRequest(@Nullable Request request) {
    this.request = request;
  }

  @Nullable
  @Override
  public final Request getRequest() {
    return request;
  }
}
