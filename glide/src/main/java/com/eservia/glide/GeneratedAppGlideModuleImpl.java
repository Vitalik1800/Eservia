package com.eservia.glide;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;

import com.eservia.glide.manager.RequestManagerRetriever;

import java.util.Collections;
import java.util.Set;

@SuppressWarnings("deprecation")
final class GeneratedAppGlideModuleImpl extends GeneratedAppGlideModule {
  private final EmptyAppModule appGlideModule;

  public GeneratedAppGlideModuleImpl(Context context) {
    appGlideModule = new EmptyAppModule();
    if (Log.isLoggable("Glide", Log.DEBUG)) {
      Log.d("Glide", "Discovered AppGlideModule from annotation: com.eservia.glide.EmptyAppModule");
    }
  }

  @Override
  public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
    appGlideModule.applyOptions(context, builder);
  }

  @Override
  public void registerComponents(@NonNull Context context, @NonNull Glide glide,
      @NonNull Registry registry) {
    appGlideModule.registerComponents(context, glide, registry);
  }

  @Override
  public boolean isManifestParsingEnabled() {
    return appGlideModule.isManifestParsingEnabled();
  }

  @Override
  @NonNull
  public Set<Class<?>> getExcludedModuleClasses() {
    return Collections.emptySet();
  }

  @Override
  @NonNull
  RequestManagerRetriever.RequestManagerFactory getRequestManagerFactory() {
    return new GeneratedRequestManagerFactory();
  }
}