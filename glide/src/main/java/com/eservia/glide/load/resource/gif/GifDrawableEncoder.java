package com.eservia.glide.load.resource.gif;

import androidx.annotation.NonNull;

import com.eservia.glide.load.EncodeStrategy;
import com.eservia.glide.load.Options;
import com.eservia.glide.load.ResourceEncoder;
import com.eservia.glide.load.engine.Resource;
import com.eservia.glide.util.ByteBufferUtil;

import java.io.File;
import java.io.IOException;


public class GifDrawableEncoder implements ResourceEncoder<GifDrawable> {
  private static final String TAG = "GifEncoder";

  @NonNull
  @Override
  public EncodeStrategy getEncodeStrategy(@NonNull Options options) {
    return EncodeStrategy.SOURCE;
  }

  @Override
  public boolean encode(
      @NonNull Resource<GifDrawable> data, @NonNull File file, @NonNull Options options) {
    GifDrawable drawable = data.get();
    boolean success = false;
    try {
      ByteBufferUtil.toFile(drawable.getBuffer(), file);
      success = true;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return success;
  }
}
