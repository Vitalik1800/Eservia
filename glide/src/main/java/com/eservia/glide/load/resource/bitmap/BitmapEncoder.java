package com.eservia.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.eservia.glide.load.EncodeStrategy;
import com.eservia.glide.load.Option;
import com.eservia.glide.load.Options;
import com.eservia.glide.load.ResourceEncoder;
import com.eservia.glide.load.data.BufferedOutputStream;
import com.eservia.glide.load.engine.Resource;
import com.eservia.glide.load.engine.bitmap_recycle.ArrayPool;
import com.eservia.glide.util.LogTime;
import com.eservia.glide.util.Util;
import com.eservia.glide.util.pool.GlideTrace;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * An {@link ResourceEncoder} that writes {@link Bitmap}s
 * to {@link OutputStream}s.
 *
 * <p>{@link Bitmap}s that return true from {@link Bitmap#hasAlpha
 * ()}} are written using {@link Bitmap.CompressFormat#PNG} to preserve alpha and
 * all other bitmaps are written using {@link Bitmap.CompressFormat#JPEG}.
 *
 * @see Bitmap#compress(Bitmap.CompressFormat, int,
 *     OutputStream)
 */
public class BitmapEncoder implements ResourceEncoder<Bitmap> {
  /**
   * An integer option between 0 and 100 that is used as the compression quality.
   *
   * <p>Defaults to 90.
   */
  public static final Option<Integer> COMPRESSION_QUALITY =
      Option.memory("com.eservia.glide.load.resource.bitmap.BitmapEncoder.CompressionQuality", 90);

  /**
   * An {@link Bitmap.CompressFormat} option used as the format to encode the
   * {@link Bitmap}.
   *
   * <p>Defaults to {@link Bitmap.CompressFormat#JPEG} for images without alpha and
   * {@link Bitmap.CompressFormat#PNG} for images with alpha.
   */
  public static final Option<Bitmap.CompressFormat> COMPRESSION_FORMAT =
      Option.memory("com.eservia.glide.load.resource.bitmap.BitmapEncoder.CompressionFormat");

  private static final String TAG = "BitmapEncoder";
  @Nullable private final ArrayPool arrayPool;

  public BitmapEncoder(@NonNull ArrayPool arrayPool) {
    this.arrayPool = arrayPool;
  }

  /**
   * @deprecated Use {@link #BitmapEncoder(ArrayPool)} instead.
   */
  @Deprecated
  public BitmapEncoder() {
    arrayPool = null;
  }

  @Override
  public boolean encode(
      @NonNull Resource<Bitmap> resource, @NonNull File file, @NonNull Options options) {
    final Bitmap bitmap = resource.get();
    Bitmap.CompressFormat format = getFormat(bitmap, options);
    GlideTrace.beginSectionFormat(
        "encode: [%dx%d] %s", bitmap.getWidth(), bitmap.getHeight(), format);
    try {
      long start = LogTime.getLogTime();
      int quality = options.get(COMPRESSION_QUALITY);

      boolean success = false;
      OutputStream os = null;
      try {
        os = new FileOutputStream(file);
        if (arrayPool != null) {
          os = new BufferedOutputStream(os, arrayPool);
        }
        bitmap.compress(format, quality, os);
        os.close();
        success = true;
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (os != null) {
          try {
            os.close();
          } catch (IOException e) {
            // Do nothing.
          }
        }
      }

      if (Log.isLoggable(TAG, Log.VERBOSE)) {
        Log.v(
            TAG,
            "Compressed with type: "
                + format
                + " of size "
                + Util.getBitmapByteSize(bitmap)
                + " in "
                + LogTime.getElapsedMillis(start)
                + ", options format: "
                + options.get(COMPRESSION_FORMAT)
                + ", hasAlpha: "
                + bitmap.hasAlpha());
      }
      return success;
    } finally {
      GlideTrace.endSection();
    }
  }

  private Bitmap.CompressFormat getFormat(Bitmap bitmap, Options options) {
    Bitmap.CompressFormat format = options.get(COMPRESSION_FORMAT);
    if (format != null) {
      return format;
    } else if (bitmap.hasAlpha()) {
      return Bitmap.CompressFormat.PNG;
    } else {
      return Bitmap.CompressFormat.JPEG;
    }
  }

  @NonNull
  @Override
  public EncodeStrategy getEncodeStrategy(@NonNull Options options) {
    return EncodeStrategy.TRANSFORMED;
  }
}
