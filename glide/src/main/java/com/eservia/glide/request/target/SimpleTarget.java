package com.eservia.glide.request.target;

import androidx.annotation.NonNull;

import com.eservia.glide.util.Util;

@Deprecated
public abstract class SimpleTarget<Z> extends BaseTarget<Z> {
  private final int width;
  private final int height;

  /**
   * Constructor for the target that uses {@link Target#SIZE_ORIGINAL} as the target width and
   * height.
   */
  // Public API.
  @SuppressWarnings("WeakerAccess")
  public SimpleTarget() {
    this(SIZE_ORIGINAL, SIZE_ORIGINAL);
  }

  /**
   * Constructor for the target that takes the desired dimensions of the decoded and/or transformed
   * resource.
   *
   * @param width The width in pixels of the desired resource.
   * @param height The height in pixels of the desired resource.
   */
  // Public API.
  @SuppressWarnings("WeakerAccess")
  public SimpleTarget(int width, int height) {
    this.width = width;
    this.height = height;
  }

  /**
   * Immediately calls the given callback with the sizes given in the constructor.
   *
   * @param cb {@inheritDoc}
   */
  @Override
  public final void getSize(@NonNull SizeReadyCallback cb) {
    if (!Util.isValidDimensions(width, height)) {
      throw new IllegalArgumentException(
          "Width and height must both be > 0 or Target#SIZE_ORIGINAL, but given"
              + " width: "
              + width
              + " and height: "
              + height
              + ", either provide dimensions in the constructor"
              + " or call override()");
    }
    cb.onSizeReady(width, height);
  }

  @Override
  public void removeCallback(@NonNull SizeReadyCallback cb) {
    // Do nothing, we never retain a reference to the callback.
  }
}
