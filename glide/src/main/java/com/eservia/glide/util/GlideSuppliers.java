package com.eservia.glide.util;

public final class GlideSuppliers {
  /**
   * Produces a non-null instance of {@code T}.
   *
   * @param <T> The data type
   */
  public interface GlideSupplier<T> {
    T get();
  }

  private GlideSuppliers() {}

  public static <T> GlideSupplier<T> memorize(final GlideSupplier<T> supplier) {
    return new GlideSupplier<T>() {
      private volatile T instance;

      @Override
      public T get() {
        if (instance == null) {
          synchronized (this) {
            if (instance == null) {
              instance = Preconditions.checkNotNull(supplier.get());
            }
          }
        }
        return instance;
      }
    };
  }
}
