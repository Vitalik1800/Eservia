package com.eservia.glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eservia.glide.manager.RequestManagerRetriever;
import com.eservia.glide.module.AppGlideModule;

import java.util.Set;

abstract class GeneratedAppGlideModule extends AppGlideModule {
  /** This method can be removed when manifest parsing is no longer supported. */
  @NonNull
  abstract Set<Class<?>> getExcludedModuleClasses();

  @Nullable
  RequestManagerRetriever.RequestManagerFactory getRequestManagerFactory() {
    return null;
  }
}
