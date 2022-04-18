package com.eservia.glide.load.resource.bitmap;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eservia.glide.load.Options;
import com.eservia.glide.load.ResourceDecoder;
import com.eservia.glide.load.engine.Resource;
import com.eservia.glide.load.engine.bitmap_recycle.BitmapPool;
import com.eservia.glide.load.resource.drawable.ResourceDrawableDecoder;

import java.io.IOException;
import java.nio.ByteBuffer;

public class ResourceBitmapDecoder implements ResourceDecoder<Uri, Bitmap> {

  private final ResourceDrawableDecoder drawableDecoder;
  private final BitmapPool bitmapPool;

  public ResourceBitmapDecoder(ResourceDrawableDecoder drawableDecoder, BitmapPool bitmapPool) {
    this.drawableDecoder = drawableDecoder;
    this.bitmapPool = bitmapPool;
  }

  @Override
  public boolean handles(@NonNull ByteBuffer source, @NonNull Options options) throws IOException {
    return ContentResolver.SCHEME_ANDROID_RESOURCE.equals(source);
  }

  @Override
  public boolean handles(@NonNull Uri source, @NonNull Options options) {
    return ContentResolver.SCHEME_ANDROID_RESOURCE.equals(source.getScheme());
  }

  @Nullable
  @Override
  public Resource<Bitmap> decode(
      @NonNull Uri source, int width, int height, @NonNull Options options) {
    Resource<Drawable> drawableResource = drawableDecoder.decode(source, width, height, options);
    if (drawableResource == null) {
      return null;
    }
    Drawable drawable = drawableResource.get();
    return DrawableToBitmapConverter.convert(bitmapPool, drawable, width, height);
  }
}
