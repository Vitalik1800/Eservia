package com.eservia.glide.load.resource.transcode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.eservia.glide.load.Options;
import com.eservia.glide.load.engine.Resource;
import com.eservia.glide.load.resource.bytes.BytesResource;
import com.eservia.glide.load.resource.gif.GifDrawable;
import com.eservia.glide.util.ByteBufferUtil;
import java.nio.ByteBuffer;

public class GifDrawableBytesTranscoder implements ResourceTranscoder<GifDrawable, byte[]> {
  @Nullable
  @Override
  public Resource<byte[]> transcode(
      @NonNull Resource<GifDrawable> toTranscode, @NonNull Options options) {
    GifDrawable gifData = toTranscode.get();
    ByteBuffer byteBuffer = gifData.getBuffer();
    return new BytesResource(ByteBufferUtil.toBytes(byteBuffer));
  }
}
