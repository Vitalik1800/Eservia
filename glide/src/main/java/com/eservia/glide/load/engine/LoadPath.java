package com.eservia.glide.load.engine;

import androidx.annotation.NonNull;
import androidx.core.util.Pools.Pool;
import com.eservia.glide.load.Options;
import com.eservia.glide.load.data.DataRewinder;
import com.eservia.glide.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LoadPath<Data, ResourceType, Transcode> {
  private final Class<Data> dataClass;
  private final Pool<List<Throwable>> listPool;
  private final List<? extends DecodePath<Data, ResourceType, Transcode>> decodePaths;
  private final String failureMessage;

  public LoadPath(
      Class<Data> dataClass,
      Class<ResourceType> resourceClass,
      Class<Transcode> transcodeClass,
      List<DecodePath<Data, ResourceType, Transcode>> decodePaths,
      Pool<List<Throwable>> listPool) {
    this.dataClass = dataClass;
    this.listPool = listPool;
    this.decodePaths = Preconditions.checkNotEmpty(decodePaths);
    failureMessage =
        "Failed LoadPath{"
            + dataClass.getSimpleName()
            + "->"
            + resourceClass.getSimpleName()
            + "->"
            + transcodeClass.getSimpleName()
            + "}";
  }

  public Resource<Transcode> load(
      DataRewinder<Data> rewinder,
      @NonNull Options options,
      int width,
      int height,
      DecodePath.DecodeCallback<ResourceType> decodeCallback)
      throws GlideException {
    List<Throwable> throwables = Preconditions.checkNotNull(listPool.acquire());
    try {
      return loadWithExceptionList(rewinder, options, width, height, decodeCallback, throwables);
    } finally {
      listPool.release(throwables);
    }
  }

  private Resource<Transcode> loadWithExceptionList(
      DataRewinder<Data> rewinder,
      @NonNull Options options,
      int width,
      int height,
      DecodePath.DecodeCallback<ResourceType> decodeCallback,
      List<Throwable> exceptions)
      throws GlideException {
    Resource<Transcode> result = null;
    //noinspection ForLoopReplaceableByForEach to improve perf
    for (int i = 0, size = decodePaths.size(); i < size; i++) {
      DecodePath<Data, ResourceType, Transcode> path = decodePaths.get(i);
      try {
        result = path.decode(rewinder, width, height, options, decodeCallback);
      } catch (GlideException | ClassNotFoundException e) {
        exceptions.add(e);
      }
      if (result != null) {
        break;
      }
    }

    if (result == null) {
      throw new GlideException(failureMessage, new ArrayList<>(exceptions));
    }

    return result;
  }

  public Class<Data> getDataClass() {
    return dataClass;
  }

  @Override
  public String toString() {
    return "LoadPath{" + "decodePaths=" + Arrays.toString(decodePaths.toArray()) + '}';
  }
}
