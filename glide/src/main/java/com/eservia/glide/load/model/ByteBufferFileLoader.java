package com.eservia.glide.load.model;

import androidx.annotation.NonNull;

import com.eservia.glide.Priority;
import com.eservia.glide.load.DataSource;
import com.eservia.glide.load.Options;
import com.eservia.glide.load.data.DataFetcher;
import com.eservia.glide.signature.ObjectKey;
import com.eservia.glide.util.ByteBufferUtil;
import com.eservia.glide.util.Synthetic;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/** Loads {@link ByteBuffer}s using NIO for {@link File}. */
public class ByteBufferFileLoader implements ModelLoader<File, ByteBuffer> {
  private static final String TAG = "ByteBufferFileLoader";

  @Override
  public LoadData<ByteBuffer> buildLoadData(
      @NonNull File file, int width, int height, @NonNull Options options) {
    return new LoadData<>(new ObjectKey(file), new ByteBufferFetcher(file));
  }

  @Override
  public boolean handles(@NonNull File file) {
    return true;
  }


  public static class Factory implements ModelLoaderFactory<File, ByteBuffer> {

    @NonNull
    @Override
    public ModelLoader<File, ByteBuffer> build(@NonNull MultiModelLoaderFactory multiFactory) {
      return new ByteBufferFileLoader();
    }

    @Override
    public void teardown() {
      // Do nothing.
    }
  }

  private static final class ByteBufferFetcher implements DataFetcher<ByteBuffer> {

    private final File file;

    @Synthetic
    @SuppressWarnings("WeakerAccess")
    ByteBufferFetcher(File file) {
      this.file = file;
    }

    @Override
    public void loadData(
        @NonNull Priority priority, @NonNull DataCallback<? super ByteBuffer> callback) throws ClassNotFoundException {
      ByteBuffer result;
      try {
        result = ByteBufferUtil.fromFile(file);
        callback.onDataReady(result);
      } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
        callback.onLoadFailed(e);
      }
    }

    @Override
    public void cleanup() {
      // Do nothing.
    }

    @Override
    public void cancel() {
      // Do nothing.
    }

    @NonNull
    @Override
    public Class<ByteBuffer> getDataClass() {
      return ByteBuffer.class;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
      return DataSource.LOCAL;
    }
  }
}
