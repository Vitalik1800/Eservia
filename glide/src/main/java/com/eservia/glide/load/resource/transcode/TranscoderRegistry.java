package com.eservia.glide.load.resource.transcode;

import androidx.annotation.NonNull;
import com.eservia.glide.util.Synthetic;
import java.util.ArrayList;
import java.util.List;


public class TranscoderRegistry {
  private final List<Entry<?, ?>> transcoders = new ArrayList<>();


  public synchronized <Z, R> void register(
      @NonNull Class<Z> decodedClass,
      @NonNull Class<R> transcodedClass,
      @NonNull ResourceTranscoder<Z, R> transcoder) {
    transcoders.add(new Entry<>(decodedClass, transcodedClass, transcoder));
  }


  @NonNull
  @SuppressWarnings("unchecked")
  public synchronized <Z, R> ResourceTranscoder<Z, R> get(
      @NonNull Class<Z> resourceClass, @NonNull Class<R> transcodedClass) {
    // For example, there may be a transcoder that can convert a GifDrawable to a Drawable, which
    // will be caught above. However, if there is no registered transcoder, we can still just use
    // the UnitTranscoder to return the Drawable because the transcode class (Drawable) is
    // assignable from the resource class (GifDrawable).
    if (transcodedClass.isAssignableFrom(resourceClass)) {
      return (ResourceTranscoder<Z, R>) UnitTranscoder.get();
    }
    for (Entry<?, ?> entry : transcoders) {
      if (entry.handles(resourceClass, transcodedClass)) {
        return (ResourceTranscoder<Z, R>) entry.transcoder;
      }
    }

    throw new IllegalArgumentException(
        "No transcoder registered to transcode from " + resourceClass + " to " + transcodedClass);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public synchronized <Z, R> List<Class<R>> getTranscodeClasses(
      @NonNull Class<Z> resourceClass, @NonNull Class<R> transcodeClass) {
    List<Class<R>> transcodeClasses = new ArrayList<>();
    // GifDrawable -> Drawable is just the UnitTranscoder, as is GifDrawable -> GifDrawable.
    if (transcodeClass.isAssignableFrom(resourceClass)) {
      transcodeClasses.add(transcodeClass);
      return transcodeClasses;
    }

    for (Entry<?, ?> entry : transcoders) {
      if (entry.handles(resourceClass, transcodeClass)
          && !transcodeClasses.contains((Class<R>) entry.toClass)) {
        transcodeClasses.add((Class<R>) entry.toClass);
      }
    }

    return transcodeClasses;
  }

  private static final class Entry<Z, R> {
    @Synthetic final Class<Z> fromClass;
    @Synthetic final Class<R> toClass;
    @Synthetic final ResourceTranscoder<Z, R> transcoder;

    Entry(
        @NonNull Class<Z> fromClass,
        @NonNull Class<R> toClass,
        @NonNull ResourceTranscoder<Z, R> transcoder) {
      this.fromClass = fromClass;
      this.toClass = toClass;
      this.transcoder = transcoder;
    }

    /**
     * If we convert from a specific Drawable, we must get that specific Drawable class or a
     * subclass of that Drawable. In contrast, if we we convert <em>to</em> a specific Drawable, we
     * can fulfill requests for a more generic parent class (like Drawable). As a result, we check
     * fromClass and toClass in different orders.
     */
    public boolean handles(@NonNull Class<?> fromClass, @NonNull Class<?> toClass) {
      return this.fromClass.isAssignableFrom(fromClass) && toClass.isAssignableFrom(this.toClass);
    }
  }
}
