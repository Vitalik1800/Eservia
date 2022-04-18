package com.eservia.glide.load.engine.cache;

import android.content.Context;
import java.io.File;


// Public API.
@SuppressWarnings({"WeakerAccess", "unused"})
public final class InternalCacheDiskCacheFactory extends DiskLruCacheFactory {

  public InternalCacheDiskCacheFactory(Context context) {
    this(
        context,
        DiskCache.Factory.DEFAULT_DISK_CACHE_DIR,
        DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE);
  }

  public InternalCacheDiskCacheFactory(Context context, long diskCacheSize) {
    this(context, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR, diskCacheSize);
  }

  public InternalCacheDiskCacheFactory(
      final Context context, final String diskCacheName, long diskCacheSize) {
    super(
        new CacheDirectoryGetter() {
          @Override
          public File getCacheDirectory() {
            File cacheDirectory = context.getCacheDir();
            if (cacheDirectory == null) {
              return null;
            }
            if (diskCacheName != null) {
              return new File(cacheDirectory, diskCacheName);
            }
            return cacheDirectory;
          }
        },
        diskCacheSize);
  }
}
