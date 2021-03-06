package com.eservia.glide.load.resource.bitmap;

import android.graphics.drawable.BitmapDrawable;
import androidx.annotation.NonNull;
import com.eservia.glide.load.engine.Initializable;
import com.eservia.glide.load.engine.bitmap_recycle.BitmapPool;
import com.eservia.glide.load.resource.drawable.DrawableResource;
import com.eservia.glide.util.Util;


public class BitmapDrawableResource extends DrawableResource<BitmapDrawable>
    implements Initializable {
  private final BitmapPool bitmapPool;

  // Public API.
  @SuppressWarnings("WeakerAccess")
  public BitmapDrawableResource(BitmapDrawable drawable, BitmapPool bitmapPool) {
    super(drawable);
    this.bitmapPool = bitmapPool;
  }

  @NonNull
  @Override
  public Class<BitmapDrawable> getResourceClass() {
    return BitmapDrawable.class;
  }

  @Override
  public int getSize() {
    return Util.getBitmapByteSize(drawable.getBitmap());
  }

  @Override
  public void recycle() {
    bitmapPool.put(drawable.getBitmap());
  }

  @Override
  public void initialize() {
    drawable.getBitmap().prepareToDraw();
  }
}
