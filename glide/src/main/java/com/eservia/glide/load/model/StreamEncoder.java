package com.eservia.glide.load.model;

import androidx.annotation.NonNull;

import com.eservia.glide.load.Encoder;
import com.eservia.glide.load.Options;
import com.eservia.glide.load.engine.bitmap_recycle.ArrayPool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * An {@link Encoder} that can write an {@link InputStream} to disk.
 */
public class StreamEncoder implements Encoder<InputStream> {
  private static final String TAG = "StreamEncoder";
  private final ArrayPool byteArrayPool;

  public StreamEncoder(ArrayPool byteArrayPool) {
    this.byteArrayPool = byteArrayPool;
  }

  @Override
  public boolean encode(@NonNull InputStream data, @NonNull File file, @NonNull Options options) {
    byte[] buffer = byteArrayPool.get(ArrayPool.STANDARD_BUFFER_SIZE_BYTES, byte[].class);
    boolean success = false;
    OutputStream os = null;
    try {
      os = new FileOutputStream(file);
      int read;
      while ((read = data.read(buffer)) != -1) {
        os.write(buffer, 0, read);
      }
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
      byteArrayPool.put(buffer);
    }
    return success;
  }
}
