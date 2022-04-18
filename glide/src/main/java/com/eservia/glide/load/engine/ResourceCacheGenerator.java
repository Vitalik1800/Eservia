package com.eservia.glide.load.engine;

import androidx.annotation.NonNull;
import com.eservia.glide.load.DataSource;
import com.eservia.glide.load.Key;
import com.eservia.glide.load.Transformation;
import com.eservia.glide.load.data.DataFetcher;
import com.eservia.glide.load.model.ModelLoader;
import com.eservia.glide.load.model.ModelLoader.LoadData;
import com.eservia.glide.util.pool.GlideTrace;
import java.io.File;
import java.util.List;


class ResourceCacheGenerator implements DataFetcherGenerator, DataFetcher.DataCallback<Object> {

  private final FetcherReadyCallback cb;
  private final DecodeHelper<?> helper;

  private int sourceIdIndex;
  private int resourceClassIndex = -1;
  private Key sourceKey;
  private List<ModelLoader<File, ?>> modelLoaders;
  private int modelLoaderIndex;
  private volatile LoadData<?> loadData;
  // PMD is wrong here, this File must be an instance variable because it may be used across
  // multiple calls to startNext.
  @SuppressWarnings("PMD.SingularField")
  private File cacheFile;

  private ResourceCacheKey currentKey;

  ResourceCacheGenerator(DecodeHelper<?> helper, FetcherReadyCallback cb) {
    this.helper = helper;
    this.cb = cb;
  }

  // See TODO below.
  @SuppressWarnings("PMD.CollapsibleIfStatements")
  @Override
  public boolean startNext() throws ClassNotFoundException {
    GlideTrace.beginSection("ResourceCacheGenerator.startNext");
    try {
      List<Key> sourceIds = helper.getCacheKeys();
      if (sourceIds.isEmpty()) {
        return false;
      }
      List<Class<?>> resourceClasses = helper.getRegisteredResourceClasses();
      if (resourceClasses.isEmpty()) {
        if (File.class.equals(helper.getTranscodeClass())) {
          return false;
        }
        throw new IllegalStateException(
            "Failed to find any load path from "
                + helper.getModelClass()
                + " to "
                + helper.getTranscodeClass());
      }
      while (modelLoaders == null || !hasNextModelLoader()) {
        resourceClassIndex++;
        if (resourceClassIndex >= resourceClasses.size()) {
          sourceIdIndex++;
          if (sourceIdIndex >= sourceIds.size()) {
            return false;
          }
          resourceClassIndex = 0;
        }

        Key sourceId = sourceIds.get(sourceIdIndex);
        Class<?> resourceClass = resourceClasses.get(resourceClassIndex);
        Transformation<?> transformation = helper.getTransformation(resourceClass);
        // PMD.AvoidInstantiatingObjectsInLoops Each iteration is comparatively expensive anyway,
        // we only run until the first one succeeds, the loop runs for only a limited
        // number of iterations on the order of 10-20 in the worst case.
        currentKey =
            new ResourceCacheKey( // NOPMD AvoidInstantiatingObjectsInLoops
                helper.getArrayPool(),
                sourceId,
                helper.getSignature(),
                helper.getWidth(),
                helper.getHeight(),
                transformation,
                resourceClass,
                helper.getOptions());
        cacheFile = helper.getDiskCache().get(currentKey);
        if (cacheFile != null) {
          sourceKey = sourceId;
          modelLoaders = helper.getModelLoaders(cacheFile);
          modelLoaderIndex = 0;
        }
      }

      loadData = null;
      boolean started = false;
      while (!started && hasNextModelLoader()) {
        ModelLoader<File, ?> modelLoader = modelLoaders.get(modelLoaderIndex++);
        loadData =
            modelLoader.buildLoadData(
                cacheFile, helper.getWidth(), helper.getHeight(), helper.getOptions());
        if (loadData != null && helper.hasLoadPath(loadData.fetcher.getDataClass())) {
          started = true;
          loadData.fetcher.loadData(helper.getPriority(), this);
        }
      }

      return started;
    }  finally {
      GlideTrace.endSection();
    }
  }

  private boolean hasNextModelLoader() {
    return modelLoaderIndex < modelLoaders.size();
  }

  @Override
  public void cancel() {
    LoadData<?> local = loadData;
    if (local != null) {
      local.fetcher.cancel();
    }
  }

  @Override
  public void onDataReady(Object data) throws ClassNotFoundException {
    cb.onDataFetcherReady(
        sourceKey, data, loadData.fetcher, DataSource.RESOURCE_DISK_CACHE, currentKey);
  }

  @Override
  public void onLoadFailed(@NonNull Exception e) throws ClassNotFoundException {
    cb.onDataFetcherFailed(currentKey, e, loadData.fetcher, DataSource.RESOURCE_DISK_CACHE);
  }
}
