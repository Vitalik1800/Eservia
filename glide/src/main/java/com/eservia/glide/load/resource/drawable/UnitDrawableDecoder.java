package com.eservia.glide.load.resource.drawable;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.eservia.glide.load.Options;
import com.eservia.glide.load.ResourceDecoder;
import com.eservia.glide.load.engine.Resource;

import java.io.IOException;
import java.nio.ByteBuffer;

/** Passes through a {@link Drawable} as a {@link Drawable} based {@link Resource}. */
public class UnitDrawableDecoder implements ResourceDecoder<Drawable, Drawable> {
  @Override
  public boolean handles(@NonNull ByteBuffer source, @NonNull Options options) throws IOException {
    return true;
  }

  @Override
  public boolean handles(@NonNull Drawable source, @NonNull Options options) {
    return true;
  }

  @Nullable
  @Override
  public Resource<Drawable> decode(
      @NonNull Drawable source, int width, int height, @NonNull Options options) {
    return NonOwnedDrawableResource.newInstance(source);
  }
}
