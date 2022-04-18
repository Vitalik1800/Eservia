package com.eservia.glide.request;

import androidx.annotation.Nullable;

import com.eservia.glide.load.DataSource;
import com.eservia.glide.load.engine.GlideException;
import com.eservia.glide.request.target.Target;


public interface RequestListener<R> {


  boolean onLoadFailed(
      @Nullable GlideException e, Object model, Target<R> target, boolean isFirstResource);


  boolean onResourceReady(
      R resource, Object model, Target<R> target, DataSource dataSource, boolean isFirstResource);
}
