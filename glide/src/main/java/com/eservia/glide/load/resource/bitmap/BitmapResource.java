package com.eservia.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.eservia.glide.load.engine.Initializable;
import com.eservia.glide.load.engine.Resource;
import com.eservia.glide.load.engine.bitmap_recycle.BitmapPool;
import com.eservia.glide.util.Preconditions;
import com.eservia.glide.util.Util;

/** A resource wrapping a {@link Bitmap} object. */
public class BitmapResource implements Resource<Bitmap>, Initializable {
  private final Bitmap bitmap;
  private final BitmapPool bitmapPool;


  @Nullable
  public static BitmapResource obtain(@Nullable Bitmap bitmap, @NonNull BitmapPool bitmapPool) {
    if (bitmap == null) {
      return null;
    } else {
      return new BitmapResource(bitmap, bitmapPool);
    }
  }

  public BitmapResource(@NonNull Bitmap bitmap, @NonNull BitmapPool bitmapPool) {
    this.bitmap = Preconditions.checkNotNull(bitmap, "Bitmap must not be null");
    this.bitmapPool = Preconditions.checkNotNull(bitmapPool, "BitmapPool must not be null");
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
    bitmapPool.put(bitmap);
  }

  @Override
  public void initialize() {
    bitmap.prepareToDraw();
  }
}
