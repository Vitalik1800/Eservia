package com.eservia.glide.load;

import android.content.Context;

import androidx.annotation.NonNull;

import com.eservia.glide.load.engine.Resource;


public interface Transformation<T> extends Key {


  @NonNull
  Resource<T> transform(
      @NonNull Context context, @NonNull Resource<T> resource, int outWidth, int outHeight);
}
