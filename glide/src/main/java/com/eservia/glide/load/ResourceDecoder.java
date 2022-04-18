package com.eservia.glide.load;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.eservia.glide.load.engine.Resource;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * An interface for decoding resources.
 *
 * @param <T> The type the resource will be decoded from (File, InputStream etc).
 * @param <Z> The type of the decoded resource (Bitmap, Drawable etc).
 */
public interface ResourceDecoder<T, Z> {


    boolean handles(@NonNull ByteBuffer source, @NonNull Options options) throws IOException;

    boolean handles(@NonNull T source, @NonNull Options options) throws IOException;

    @Nullable
  Resource<Z> decode(@NonNull T source, int width, int height, @NonNull Options options)
            throws IOException, ClassNotFoundException;
}
