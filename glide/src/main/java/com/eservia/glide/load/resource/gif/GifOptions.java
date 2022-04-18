package com.eservia.glide.load.resource.gif;

import com.eservia.glide.load.DecodeFormat;
import com.eservia.glide.load.Option;

/** Options related to decoding GIFs. */
public final class GifOptions {

  /**
   * Indicates the {@link DecodeFormat} that will be used in conjunction
   * with the particular GIF to determine the {@link android.graphics.Bitmap.Config} to use when
   * decoding frames of GIFs.
   */
  public static final Option<DecodeFormat> DECODE_FORMAT =
      Option.memory(
          "com.eservia.glide.load.resource.gif.GifOptions.DecodeFormat", DecodeFormat.DEFAULT);


  public static final Option<Boolean> DISABLE_ANIMATION =
      Option.memory("com.eservia.glide.load.resource.gif.GifOptions.DisableAnimation", false);

  private GifOptions() {
    // Utility class.
  }
}
