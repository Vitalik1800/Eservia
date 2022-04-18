package com.eservia.glide.request.target;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.Nullable;


// Public API.
@SuppressWarnings("WeakerAccess")
public abstract class ThumbnailImageViewTarget<T> extends ImageViewTarget<T> {

  public ThumbnailImageViewTarget(ImageView view) {
    super(view);
  }

  /**
   * @deprecated Use {@link #waitForLayout()} insetad.
   */
  @Deprecated
  @SuppressWarnings({"deprecation"})
  public ThumbnailImageViewTarget(ImageView view, boolean waitForLayout) {
    super(view, waitForLayout);
  }

  @Override
  protected void setResource(@Nullable T resource) {
    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
    Drawable result = getDrawable(resource);
    if (layoutParams != null && layoutParams.width > 0 && layoutParams.height > 0) {
      result = new FixedSizeDrawable(result, layoutParams.width, layoutParams.height);
    }

    view.setImageDrawable(result);
  }

  protected abstract Drawable getDrawable(T resource);
}
