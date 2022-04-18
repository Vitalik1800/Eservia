package com.eservia.glide.module;

import android.content.Context;
import androidx.annotation.NonNull;
import com.eservia.glide.Glide;
import com.eservia.glide.Registry;


@SuppressWarnings("deprecation")
public abstract class LibraryGlideModule implements RegistersComponents {
  @Override
  public void registerComponents(
      @NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
    // Default empty impl.
  }
}
