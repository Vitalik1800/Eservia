package com.eservia.glide.load.resource.bitmap;

import android.media.ExifInterface;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.eservia.glide.load.ImageHeaderParser;
import com.eservia.glide.load.engine.bitmap_recycle.ArrayPool;
import com.eservia.glide.util.ByteBufferUtil;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;


@RequiresApi(Build.VERSION_CODES.O_MR1)
public final class ExifInterfaceImageHeaderParser implements ImageHeaderParser {

  @NonNull
  @Override
  public ImageType getType(@NonNull InputStream is) {
    return ImageType.UNKNOWN;
  }

  @NonNull
  @Override
  public ImageType getType(@NonNull ByteBuffer byteBuffer) {
    return ImageType.UNKNOWN;
  }

  @Override
  public int getOrientation(@NonNull InputStream is, @NonNull ArrayPool byteArrayPool)
      throws IOException {
    ExifInterface exifInterface = new ExifInterface(is);
    int result =
        exifInterface.getAttributeInt(
            ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
    if (result == ExifInterface.ORIENTATION_UNDEFINED) {
      return ImageHeaderParser.UNKNOWN_ORIENTATION;
    }
    return result;
  }

  @Override
  public int getOrientation(@NonNull ByteBuffer byteBuffer, @NonNull ArrayPool byteArrayPool)
      throws IOException {
    return getOrientation(ByteBufferUtil.toStream(byteBuffer), byteArrayPool);
  }
}
