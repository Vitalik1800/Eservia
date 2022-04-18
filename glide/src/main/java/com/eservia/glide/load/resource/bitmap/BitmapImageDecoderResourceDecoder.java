package com.eservia.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.ImageDecoder.Source;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.eservia.glide.load.Options;
import com.eservia.glide.load.ResourceDecoder;
import com.eservia.glide.load.engine.Resource;
import com.eservia.glide.load.engine.bitmap_recycle.BitmapPool;
import com.eservia.glide.load.engine.bitmap_recycle.BitmapPoolAdapter;
import com.eservia.glide.load.resource.DefaultOnHeaderDecodedListener;
import java.io.IOException;
import java.nio.ByteBuffer;

/** {@link Bitmap} specific implementation of {@link DefaultOnHeaderDecodedListener}. */
@RequiresApi(api = 28)
public final class BitmapImageDecoderResourceDecoder implements ResourceDecoder<Source, Bitmap> {
  private static final String TAG = "BitmapImageDecoder";
  private final BitmapPool bitmapPool = new BitmapPoolAdapter();

  @Override
  public boolean handles(@NonNull ByteBuffer source, @NonNull Options options) throws IOException {
    return true;
  }

  @Override
  public boolean handles(@NonNull Source source, @NonNull Options options) throws IOException {
    return true;
  }

  @Override
  public Resource<Bitmap> decode(
      @NonNull Source source, int width, int height, @NonNull Options options) throws IOException {
    Bitmap result =
        ImageDecoder.decodeBitmap(
            source, new DefaultOnHeaderDecodedListener(width, height, options));
    if (Log.isLoggable(TAG, Log.VERBOSE)) {
      Log.v(
          TAG,
          "Decoded"
              + " ["
              + result.getWidth()
              + "x"
              + result.getHeight()
              + "]"
              + " for ["
              + width
              + "x"
              + height
              + "]");
    }
    return new BitmapResource(result, bitmapPool);
  }
}
