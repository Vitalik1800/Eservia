package com.eservia.glide.load.data;

import androidx.annotation.NonNull;
import java.io.IOException;

/**
 * Responsible for rewinding a stream like data types.
 *
 * @param <T> The stream like data type that can be rewound.
 */
public interface DataRewinder<T> {


  interface Factory<T> {
    @NonNull
    DataRewinder<T> build(@NonNull T data);

    @NonNull
    Class<T> getDataClass();
  }

  /**
   * Rewinds the wrapped data back to the beginning and returns the re-wound data (or a wrapper for
   * the re-wound data).
   *
   * @return An object pointing to the wrapped data.
   */
  @NonNull
  T rewindAndGet() throws IOException;

  /**
   * Called when this rewinder is no longer needed and can be cleaned up.
   *
   * <p>The underlying data may still be in use and should not be closed or invalidated.
   */
  void cleanup();
}
