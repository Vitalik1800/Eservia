package com.eservia.glide.load.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pools.Pool;

import com.eservia.glide.Priority;
import com.eservia.glide.load.DataSource;
import com.eservia.glide.load.Options;
import com.eservia.glide.load.data.DataFetcher;
import com.eservia.glide.load.engine.GlideException;
import com.eservia.glide.util.Preconditions;
import com.eservia.glide.load.Key;
import com.eservia.glide.load.data.DataFetcher.DataCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class MultiModelLoader<Model, Data> implements ModelLoader<Model, Data> {

  private final List<ModelLoader<Model, Data>> modelLoaders;
  private final Pool<List<Throwable>> exceptionListPool;

  MultiModelLoader(
      @NonNull List<ModelLoader<Model, Data>> modelLoaders,
      @NonNull Pool<List<Throwable>> exceptionListPool) {
    this.modelLoaders = modelLoaders;
    this.exceptionListPool = exceptionListPool;
  }

  @Override
  public LoadData<Data> buildLoadData(
      @NonNull Model model, int width, int height, @NonNull Options options) throws ClassNotFoundException {
    Key sourceKey = null;
    int size = modelLoaders.size();
    List<DataFetcher<Data>> fetchers = new ArrayList<>(size);
    //noinspection ForLoopReplaceableByForEach to improve perf
    for (int i = 0; i < size; i++) {
      ModelLoader<Model, Data> modelLoader = modelLoaders.get(i);
      if (modelLoader.handles(model)) {
        LoadData<Data> loadData = modelLoader.buildLoadData(model, width, height, options);
        if (loadData != null) {
          sourceKey = loadData.sourceKey;
          fetchers.add(loadData.fetcher);
        }
      }
    }
    return !fetchers.isEmpty() && sourceKey != null
        ? new LoadData<>(sourceKey, new MultiFetcher<>(fetchers, exceptionListPool))
        : null;
  }

  @Override
  public boolean handles(@NonNull Model model) {
    for (ModelLoader<Model, Data> modelLoader : modelLoaders) {
      if (modelLoader.handles(model)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return "MultiModelLoader{" + "modelLoaders=" + Arrays.toString(modelLoaders.toArray()) + '}';
  }

  static class MultiFetcher<Data> implements DataFetcher<Data>, DataCallback<Data> {

    private final List<DataFetcher<Data>> fetchers;
    private final Pool<List<Throwable>> throwableListPool;
    private int currentIndex;
    private Priority priority;
    private DataCallback<? super Data> callback;
    @Nullable private List<Throwable> exceptions;
    private boolean isCancelled;

    MultiFetcher(
        @NonNull List<DataFetcher<Data>> fetchers,
        @NonNull Pool<List<Throwable>> throwableListPool) {
      this.throwableListPool = throwableListPool;
      Preconditions.checkNotEmpty(fetchers);
      this.fetchers = fetchers;
      currentIndex = 0;
    }

    @Override
    public void loadData(@NonNull Priority priority, @NonNull DataCallback<? super Data> callback) throws ClassNotFoundException {
      this.priority = priority;
      this.callback = callback;
      exceptions = throwableListPool.acquire();
      fetchers.get(currentIndex).loadData(priority, this);

      // If a race occurred where we cancelled the fetcher in cancel() and then called loadData here
      // immediately after, make sure that we cancel the newly started fetcher. We don't bother
      // checking cancelled before loadData because it's not required for correctness and would
      // require an unlikely race to be useful.
      if (isCancelled) {
        cancel();
      }
    }

    @Override
    public void cleanup() {
      if (exceptions != null) {
        throwableListPool.release(exceptions);
      }
      exceptions = null;
      for (DataFetcher<Data> fetcher : fetchers) {
        fetcher.cleanup();
      }
    }

    @Override
    public void cancel() {
      isCancelled = true;
      for (DataFetcher<Data> fetcher : fetchers) {
        fetcher.cancel();
      }
    }

    @NonNull
    @Override
    public Class<Data> getDataClass() {
      return fetchers.get(0).getDataClass();
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
      return fetchers.get(0).getDataSource();
    }

    @Override
    public void onDataReady(@Nullable Data data) throws ClassNotFoundException {
      if (data != null) {
        callback.onDataReady(data);
      } else {
        startNextOrFail();
      }
    }

    @Override
    public void onLoadFailed(@NonNull Exception e) throws ClassNotFoundException {
      Preconditions.checkNotNull(exceptions).add(e);
      startNextOrFail();
    }

    private void startNextOrFail() throws ClassNotFoundException {
      if (isCancelled) {
        return;
      }

      if (currentIndex < fetchers.size() - 1) {
        currentIndex++;
        loadData(priority, callback);
      } else {
        Preconditions.checkNotNull(exceptions);
        callback.onLoadFailed(new GlideException("Fetch failed", new ArrayList<>(exceptions)));
      }
    }
  }
}
