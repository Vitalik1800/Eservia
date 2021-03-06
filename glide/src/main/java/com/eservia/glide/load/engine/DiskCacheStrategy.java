package com.eservia.glide.load.engine;

import com.eservia.glide.load.DataSource;
import com.eservia.glide.load.EncodeStrategy;

/** Set of available caching strategies for media. */
public abstract class DiskCacheStrategy {

  /**
   * Caches remote data with both {@link #DATA} and {@link #RESOURCE}, and local data with {@link
   * #RESOURCE} only.
   */
  public static final DiskCacheStrategy ALL =
      new DiskCacheStrategy() {
        @Override
        public boolean isDataCacheable(DataSource dataSource) {
          return dataSource == DataSource.REMOTE;
        }

        @Override
        public boolean isResourceCacheable(
            boolean isFromAlternateCacheKey, DataSource dataSource, EncodeStrategy encodeStrategy) {
          return dataSource != DataSource.RESOURCE_DISK_CACHE
              && dataSource != DataSource.MEMORY_CACHE;
        }

        @Override
        public boolean decodeCachedResource() {
          return true;
        }

        @Override
        public boolean decodeCachedData() {
          return true;
        }
      };

  /** Saves no data to cache. */
  public static final DiskCacheStrategy NONE =
      new DiskCacheStrategy() {
        @Override
        public boolean isDataCacheable(DataSource dataSource) {
          return false;
        }

        @Override
        public boolean isResourceCacheable(
            boolean isFromAlternateCacheKey, DataSource dataSource, EncodeStrategy encodeStrategy) {
          return false;
        }

        @Override
        public boolean decodeCachedResource() {
          return false;
        }

        @Override
        public boolean decodeCachedData() {
          return false;
        }
      };

  /** Writes retrieved data directly to the disk cache before it's decoded. */
  public static final DiskCacheStrategy DATA =
      new DiskCacheStrategy() {
        @Override
        public boolean isDataCacheable(DataSource dataSource) {
          return dataSource != DataSource.DATA_DISK_CACHE && dataSource != DataSource.MEMORY_CACHE;
        }

        @Override
        public boolean isResourceCacheable(
            boolean isFromAlternateCacheKey, DataSource dataSource, EncodeStrategy encodeStrategy) {
          return false;
        }

        @Override
        public boolean decodeCachedResource() {
          return false;
        }

        @Override
        public boolean decodeCachedData() {
          return true;
        }
      };

  /** Writes resources to disk after they've been decoded. */
  public static final DiskCacheStrategy RESOURCE =
      new DiskCacheStrategy() {
        @Override
        public boolean isDataCacheable(DataSource dataSource) {
          return false;
        }

        @Override
        public boolean isResourceCacheable(
            boolean isFromAlternateCacheKey, DataSource dataSource, EncodeStrategy encodeStrategy) {
          return dataSource != DataSource.RESOURCE_DISK_CACHE
              && dataSource != DataSource.MEMORY_CACHE;
        }

        @Override
        public boolean decodeCachedResource() {
          return true;
        }

        @Override
        public boolean decodeCachedData() {
          return false;
        }
      };


  public static final DiskCacheStrategy AUTOMATIC =
      new DiskCacheStrategy() {
        @Override
        public boolean isDataCacheable(DataSource dataSource) {
          return dataSource == DataSource.REMOTE;
        }

        @Override
        public boolean isResourceCacheable(
            boolean isFromAlternateCacheKey, DataSource dataSource, EncodeStrategy encodeStrategy) {
          return ((isFromAlternateCacheKey && dataSource == DataSource.DATA_DISK_CACHE)
                  || dataSource == DataSource.LOCAL)
              && encodeStrategy == EncodeStrategy.TRANSFORMED;
        }

        @Override
        public boolean decodeCachedResource() {
          return true;
        }

        @Override
        public boolean decodeCachedData() {
          return true;
        }
      };

  /**
   * Returns true if this request should cache the original unmodified data.
   *
   * @param dataSource Indicates where the data was originally retrieved.
   */
  public abstract boolean isDataCacheable(DataSource dataSource);


  public abstract boolean isResourceCacheable(
      boolean isFromAlternateCacheKey, DataSource dataSource, EncodeStrategy encodeStrategy);

  /** Returns true if this request should attempt to decode cached resource data. */
  public abstract boolean decodeCachedResource();

  /** Returns true if this request should attempt to decode cached source data. */
  public abstract boolean decodeCachedData();
}
