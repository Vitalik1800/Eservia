package com.eservia.glide.load.resource.gif;

import androidx.annotation.NonNull;
import com.eservia.glide.load.engine.Initializable;
import com.eservia.glide.load.resource.drawable.DrawableResource;

public class GifDrawableResource extends DrawableResource<GifDrawable> implements Initializable {
  // Public API.
  @SuppressWarnings("WeakerAccess")
  public GifDrawableResource(GifDrawable drawable) {
    super(drawable);
  }

  @NonNull
  @Override
  public Class<GifDrawable> getResourceClass() {
    return GifDrawable.class;
  }

  @Override
  public int getSize() {
    return drawable.getSize();
  }

  @Override
  public void recycle() {
    drawable.stop();
    drawable.recycle();
  }

  @Override
  public void initialize() {
    drawable.getFirstFrame().prepareToDraw();
  }
}
