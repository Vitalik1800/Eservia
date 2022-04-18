package com.eservia.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.eservia.glide.load.Options;
import com.eservia.glide.load.ResourceDecoder;
import com.eservia.glide.load.engine.Resource;
import com.eservia.glide.util.Util;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Passes through a (hopefully) non-owned {@link Bitmap} as a {@link Bitmap} based {@link Resource}
 * so that the given {@link Bitmap} is not recycled.
 */
public final class UnitBitmapDecoder implements ResourceDecoder<Bitmap, Bitmap> {

  @Override
  public boolean handles(@NonNull ByteBuffer source, @NonNull Options options) throws IOException {
    return true;
  }

  @Override
  public boolean handles(@NonNull Bitmap source, @NonNull Options options) {
    return true;
  }

  @Override
  public Resource<Bitmap> decode(
      @NonNull Bitmap source, int width, int height, @NonNull Options options) {
    return new NonOwnedBitmapResource(source);
  }

  private static final class NonOwnedBitmapResource implements Resource<Bitmap> {

    private final Bitmap bitmap;

    NonOwnedBitmapResource(@NonNull Bitmap bitmap) {
      this.bitmap = bitmap;
    }

    @NonNull
    @Override
    public Class<Bitmap> getResourceClass() {
      return Bitmap.class;
    }

    @NonNull
    @Override
    public Bitmap get() {
      return bitmap;
    }

    @Override
    public int getSize() {
      return Util.getBitmapByteSize(bitmap);
    }

    @Override
    public void recycle() {
      // Do nothing.
    }
  }
}
