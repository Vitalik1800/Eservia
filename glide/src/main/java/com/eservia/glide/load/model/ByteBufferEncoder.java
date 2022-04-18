package com.eservia.glide.load.model;

import androidx.annotation.NonNull;

import com.eservia.glide.load.Encoder;
import com.eservia.glide.load.Options;
import com.eservia.glide.util.ByteBufferUtil;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/** Writes {@link ByteBuffer ByteBuffers} to {@link File Files}. */
public class ByteBufferEncoder implements Encoder<ByteBuffer> {
  private static final String TAG = "ByteBufferEncoder";

  @Override
  public boolean encode(@NonNull ByteBuffer data, @NonNull File file, @NonNull Options options) {
    boolean success = false;
    try {
      ByteBufferUtil.toFile(data, file);
      success = true;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return success;
  }
}
