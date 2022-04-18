package com.eservia.glide.module;

import android.content.Context;
import androidx.annotation.NonNull;
import com.eservia.glide.GlideBuilder;


public abstract class AppGlideModule extends LibraryGlideModule implements AppliesOptions {


  public boolean isManifestParsingEnabled() {
    return true;
  }

  @Override
  public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
    // Default empty impl.
  }
}
