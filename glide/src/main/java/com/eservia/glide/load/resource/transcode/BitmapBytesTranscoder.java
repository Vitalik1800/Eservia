package com.eservia.glide.load.resource.transcode;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.eservia.glide.load.Options;
import com.eservia.glide.load.engine.Resource;
import com.eservia.glide.load.resource.bytes.BytesResource;
import java.io.ByteArrayOutputStream;

public class BitmapBytesTranscoder implements ResourceTranscoder<Bitmap, byte[]> {
  private final Bitmap.CompressFormat compressFormat;
  private final int quality;

  public BitmapBytesTranscoder() {
    this(Bitmap.CompressFormat.JPEG, 100);
  }

  // Public API.
  @SuppressWarnings("WeakerAccess")
  public BitmapBytesTranscoder(@NonNull Bitmap.CompressFormat compressFormat, int quality) {
    this.compressFormat = compressFormat;
    this.quality = quality;
  }

  @Nullable
  @Override
  public Resource<byte[]> transcode(
      @NonNull Resource<Bitmap> toTranscode, @NonNull Options options) {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    toTranscode.get().compress(compressFormat, quality, os);
    toTranscode.recycle();
    return new BytesResource(os.toByteArray());
  }
}
