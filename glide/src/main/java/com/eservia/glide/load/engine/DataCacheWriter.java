package com.eservia.glide.load.engine;

import androidx.annotation.NonNull;
import com.eservia.glide.load.Encoder;
import com.eservia.glide.load.Options;
import com.eservia.glide.load.engine.cache.DiskCache;
import java.io.File;


class DataCacheWriter<DataType> implements DiskCache.Writer {
  private final Encoder<DataType> encoder;
  private final DataType data;
  private final Options options;

  DataCacheWriter(Encoder<DataType> encoder, DataType data, Options options) {
    this.encoder = encoder;
    this.data = data;
    this.options = options;
  }

  @Override
  public boolean write(@NonNull File file) {
    return encoder.encode(data, file, options);
  }
}
