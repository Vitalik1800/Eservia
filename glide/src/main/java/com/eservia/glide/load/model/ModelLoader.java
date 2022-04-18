package com.eservia.glide.load.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.eservia.glide.load.Key;
import com.eservia.glide.load.Options;
import com.eservia.glide.load.data.DataFetcher;
import com.eservia.glide.util.Preconditions;
import java.util.Collections;
import java.util.List;

public interface ModelLoader<Model, Data> {


  class LoadData<Data> {
    public final Key sourceKey;
    public final List<Key> alternateKeys;
    public final DataFetcher<Data> fetcher;

    public LoadData(@NonNull Key sourceKey, @NonNull DataFetcher<Data> fetcher) {
      this(sourceKey, Collections.<Key>emptyList(), fetcher);
    }

    public LoadData(
        @NonNull Key sourceKey,
        @NonNull List<Key> alternateKeys,
        @NonNull DataFetcher<Data> fetcher) {
      this.sourceKey = Preconditions.checkNotNull(sourceKey);
      this.alternateKeys = Preconditions.checkNotNull(alternateKeys);
      this.fetcher = Preconditions.checkNotNull(fetcher);
    }
  }


  @Nullable
  LoadData<Data> buildLoadData(
      @NonNull Model model, int width, int height, @NonNull Options options) throws ClassNotFoundException;


  boolean handles(@NonNull Model model);
}
