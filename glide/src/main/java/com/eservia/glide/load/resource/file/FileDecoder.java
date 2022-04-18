package com.eservia.glide.load.resource.file;

import androidx.annotation.NonNull;
import com.eservia.glide.load.Options;
import com.eservia.glide.load.ResourceDecoder;
import com.eservia.glide.load.engine.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * A simple {@link ResourceDecoder} that creates resource for a given {@link
 * File}.
 */
public class FileDecoder implements ResourceDecoder<File, File> {

  @Override
  public boolean handles(@NonNull ByteBuffer source, @NonNull Options options) throws IOException {
    return true;
  }

  @Override
  public boolean handles(@NonNull File source, @NonNull Options options) {
    return true;
  }

  @Override
  public Resource<File> decode(
      @NonNull File source, int width, int height, @NonNull Options options) {
    return new FileResource(source);
  }
}
