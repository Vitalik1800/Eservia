package com.eservia.glide.load.engine;

import androidx.annotation.Nullable;
import com.eservia.glide.load.DataSource;
import com.eservia.glide.load.Key;
import com.eservia.glide.load.data.DataFetcher;

interface DataFetcherGenerator {

  interface FetcherReadyCallback {

    /** Requests that we call startNext() again on a Glide owned thread. */
    void reschedule();

    /**
     * Notifies the callback that the load is complete.
     *
     * @param sourceKey The id of the loaded data.
     * @param data The loaded data, or null if the load failed.
     * @param fetcher The data fetcher we attempted to load from.
     * @param dataSource The data source we were loading from.
     * @param attemptedKey The key we were loading data from (may be an alternate).
     */
    void onDataFetcherReady(
        Key sourceKey,
        @Nullable Object data,
        DataFetcher<?> fetcher,
        DataSource dataSource,
        Key attemptedKey) throws ClassNotFoundException;

    /**
     * Notifies the callback when the load fails.
     *
     * @param attemptedKey The key we were using to load (may be an alternate).
     * @param e The exception that caused the load to fail.
     * @param fetcher The fetcher we were loading from.
     * @param dataSource The data source we were loading from.
     */
    void onDataFetcherFailed(
        Key attemptedKey, Exception e, DataFetcher<?> fetcher, DataSource dataSource) throws ClassNotFoundException;
  }

  boolean startNext() throws ClassNotFoundException;

  /**
   * Attempts to cancel the currently running fetcher.
   *
   * <p>This will be called on the main thread and should complete quickly.
   */
  void cancel();
}
