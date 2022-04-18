package com.eservia.glide.load.model;

import androidx.annotation.NonNull;

public interface ModelLoaderFactory<T, Y> {

  /**
   * Build a concrete ModelLoader for this model type.
   *
   * @param multiFactory A map of classes to factories that can be used to construct additional
   *     {@link ModelLoader}s that this factory's {@link ModelLoader} may depend on
   * @return A new {@link ModelLoader}
   */
  @NonNull
  ModelLoader<T, Y> build(@NonNull MultiModelLoaderFactory multiFactory);

  /** A lifecycle method that will be called when this factory is about to replaced. */
  void teardown();
}
