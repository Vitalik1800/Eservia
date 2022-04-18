package com.eservia.glide;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;

import com.eservia.glide.load.engine.Engine;
import com.eservia.glide.load.engine.bitmap_recycle.ArrayPool;
import com.eservia.glide.load.engine.bitmap_recycle.BitmapPool;
import com.eservia.glide.load.engine.bitmap_recycle.BitmapPoolAdapter;
import com.eservia.glide.load.engine.bitmap_recycle.LruArrayPool;
import com.eservia.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.eservia.glide.load.engine.cache.DiskCache;
import com.eservia.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.eservia.glide.load.engine.cache.LruResourceCache;
import com.eservia.glide.load.engine.cache.MemoryCache;
import com.eservia.glide.load.engine.cache.MemorySizeCalculator;
import com.eservia.glide.load.engine.executor.GlideExecutor;
import com.eservia.glide.manager.ConnectivityMonitorFactory;
import com.eservia.glide.manager.DefaultConnectivityMonitorFactory;
import com.eservia.glide.manager.RequestManagerRetriever;
import com.eservia.glide.module.AppGlideModule;
import com.eservia.glide.module.GlideModule;
import com.eservia.glide.request.RequestListener;
import com.eservia.glide.request.RequestOptions;
import com.eservia.glide.util.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/** A builder class for setting default structural classes for Glide to use. */
@SuppressWarnings("PMD.ImmutableField")
public final class GlideBuilder {
  private final Map<Class<?>, TransitionOptions<?, ?>> defaultTransitionOptions = new ArrayMap<>();
  private final GlideExperiments.Builder glideExperimentsBuilder = new GlideExperiments.Builder();
  private Engine engine;
  private BitmapPool bitmapPool;
  private ArrayPool arrayPool;
  private MemoryCache memoryCache;
  private GlideExecutor sourceExecutor;
  private GlideExecutor diskCacheExecutor;
  private DiskCache.Factory diskCacheFactory;
  private MemorySizeCalculator memorySizeCalculator;
  private ConnectivityMonitorFactory connectivityMonitorFactory;
  private int logLevel = Log.INFO;
  private Glide.RequestOptionsFactory defaultRequestOptionsFactory =
      new Glide.RequestOptionsFactory() {
        @NonNull
        @Override
        public RequestOptions build() {
          return new RequestOptions();
        }
      };
  @Nullable private RequestManagerRetriever.RequestManagerFactory requestManagerFactory;
  private GlideExecutor animationExecutor;
  private boolean isActiveResourceRetentionAllowed;
  @Nullable private List<RequestListener<Object>> defaultRequestListeners;

  @NonNull
  public GlideBuilder setBitmapPool(@Nullable BitmapPool bitmapPool) {
    this.bitmapPool = bitmapPool;
    return this;
  }

  /**
   * Sets the {@link ArrayPool} implementation to allow variable sized arrays to be stored and
   * retrieved as needed.
   *
   * @param arrayPool The pool to use.
   * @return This builder.
   */
  @NonNull
  public GlideBuilder setArrayPool(@Nullable ArrayPool arrayPool) {
    this.arrayPool = arrayPool;
    return this;
  }

  // Public API.
  @SuppressWarnings("WeakerAccess")
  @NonNull
  public GlideBuilder setMemoryCache(@Nullable MemoryCache memoryCache) {
    this.memoryCache = memoryCache;
    return this;
  }

  // Public API.
  @SuppressWarnings("WeakerAccess")
  @NonNull
  public GlideBuilder setDiskCache(@Nullable DiskCache.Factory diskCacheFactory) {
    this.diskCacheFactory = diskCacheFactory;
    return this;
  }

  @Deprecated
  public GlideBuilder setResizeExecutor(@Nullable GlideExecutor service) {
    return setSourceExecutor(service);
  }


  // Public API.
  @SuppressWarnings("WeakerAccess")
  @NonNull
  public GlideBuilder setSourceExecutor(@Nullable GlideExecutor service) {
    this.sourceExecutor = service;
    return this;
  }

  // Public API.
  @SuppressWarnings("WeakerAccess")
  @NonNull
  public GlideBuilder setDiskCacheExecutor(@Nullable GlideExecutor service) {
    this.diskCacheExecutor = service;
    return this;
  }

  // Public API.
  @SuppressWarnings("WeakerAccess")
  @NonNull
  public GlideBuilder setAnimationExecutor(@Nullable GlideExecutor service) {
    this.animationExecutor = service;
    return this;
  }

  @NonNull
  public GlideBuilder setDefaultRequestOptions(@Nullable final RequestOptions requestOptions) {
    return setDefaultRequestOptions(
        new Glide.RequestOptionsFactory() {
          @NonNull
          @Override
          public RequestOptions build() {
            return requestOptions != null ? requestOptions : new RequestOptions();
          }
        });
  }


  @NonNull
  public GlideBuilder setDefaultRequestOptions(@NonNull Glide.RequestOptionsFactory factory) {
    this.defaultRequestOptionsFactory = Preconditions.checkNotNull(factory);
    return this;
  }


  // Public API.
  @SuppressWarnings("unused")
  @NonNull
  public <T> GlideBuilder setDefaultTransitionOptions(
      @NonNull Class<T> clazz, @Nullable TransitionOptions<?, T> options) {
    defaultTransitionOptions.put(clazz, options);
    return this;
  }

  /**
   * Sets the {@link MemorySizeCalculator} to use to calculate maximum sizes for default {@link
   * MemoryCache MemoryCaches} and/or default {@link BitmapPool BitmapPools}.
   *
   * @see #setMemorySizeCalculator(MemorySizeCalculator)
   * @param builder The builder to use (will not be modified).
   * @return This builder.
   */
  // Public API.
  @SuppressWarnings("unused")
  @NonNull
  public GlideBuilder setMemorySizeCalculator(@NonNull MemorySizeCalculator.Builder builder) {
    return setMemorySizeCalculator(builder.build());
  }

  /**
   * Sets the {@link MemorySizeCalculator} to use to calculate maximum sizes for default {@link
   * MemoryCache MemoryCaches} and/or default {@link BitmapPool BitmapPools}.
   *
   * <p>The given {@link MemorySizeCalculator} will not affect custom pools or caches provided via
   * {@link #setBitmapPool(BitmapPool)} or {@link #setMemoryCache(MemoryCache)}.
   *
   * @param calculator The calculator to use.
   * @return This builder.
   */
  // Public API.
  @SuppressWarnings("WeakerAccess")
  @NonNull
  public GlideBuilder setMemorySizeCalculator(@Nullable MemorySizeCalculator calculator) {
    this.memorySizeCalculator = calculator;
    return this;
  }

  // Public API.
  @SuppressWarnings("unused")
  @NonNull
  public GlideBuilder setConnectivityMonitorFactory(@Nullable ConnectivityMonitorFactory factory) {
    this.connectivityMonitorFactory = factory;
    return this;
  }

  /**
   * Sets a log level constant from those in {@link Log} to indicate the desired log verbosity.
   *
   * <p>The level must be one of {@link Log#VERBOSE}, {@link Log#DEBUG}, {@link Log#INFO}, {@link
   * Log#WARN}, or {@link Log#ERROR}.
   *
   * <p>{@link Log#VERBOSE} means one or more lines will be logged per request, including timing
   * logs and failures. {@link Log#DEBUG} means at most one line will be logged per successful
   * request, including timing logs, although many lines may be logged for failures including
   * multiple complete stack traces. {@link Log#INFO} means failed loads will be logged including
   * multiple complete stack traces, but successful loads will not be logged at all. {@link
   * Log#WARN} means only summaries of failed loads will be logged. {@link Log#ERROR} means only
   * exceptional cases will be logged.
   *
   * <p>All logs will be logged using the 'Glide' tag.
   *
   * <p>Many other debugging logs are available in individual classes. The log level supplied here
   * only controls a small set of informative and well formatted logs. Users wishing to debug
   * certain aspects of the library can look for individual <code>TAG</code> variables at the tops
   * of classes and use <code>adb shell setprop log.tag.TAG</code> to enable or disable any relevant
   * tags.
   *
   * @param logLevel The log level to use from {@link Log}.
   * @return This builder.
   */
  // Public API.
  @SuppressWarnings("unused")
  @NonNull
  public GlideBuilder setLogLevel(int logLevel) {
    if (logLevel < Log.VERBOSE || logLevel > Log.ERROR) {
      throw new IllegalArgumentException(
          "Log level must be one of Log.VERBOSE, Log.DEBUG," + " Log.INFO, Log.WARN, or Log.ERROR");
    }
    this.logLevel = logLevel;
    return this;
  }

  // Public API.
  @SuppressWarnings("unused")
  @NonNull
  public GlideBuilder setIsActiveResourceRetentionAllowed(
      boolean isActiveResourceRetentionAllowed) {
    this.isActiveResourceRetentionAllowed = isActiveResourceRetentionAllowed;
    return this;
  }

  @NonNull
  public GlideBuilder addGlobalRequestListener(@NonNull RequestListener<Object> listener) {
    if (defaultRequestListeners == null) {
      defaultRequestListeners = new ArrayList<>();
    }
    defaultRequestListeners.add(listener);
    return this;
  }

  public GlideBuilder setLogRequestOrigins(boolean isEnabled) {
    glideExperimentsBuilder.update(new LogRequestOrigins(), isEnabled);
    return this;
  }

  public GlideBuilder setImageDecoderEnabledForBitmaps(boolean isEnabled) {
    glideExperimentsBuilder.update(
        new EnableImageDecoderForBitmaps(),
        /*isEnabled=*/ isEnabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q);
    return this;
  }

  /** This is an experimental method, it may be removed or changed at any future version. */
  public GlideBuilder setEnableImageDecoderForAnimatedWebp(
      boolean enableImageDecoderForAnimatedWebp) {
    glideExperimentsBuilder.update(
        new EnableImageDecoderForAnimatedWebp(), enableImageDecoderForAnimatedWebp);
    return this;
  }

  void setRequestManagerFactory(@Nullable RequestManagerRetriever.RequestManagerFactory factory) {
    this.requestManagerFactory = factory;
  }

  // For testing.
  GlideBuilder setEngine(Engine engine) {
    this.engine = engine;
    return this;
  }

  @NonNull
  Glide build(
      @NonNull Context context,
      List<GlideModule> manifestModules,
      AppGlideModule annotationGeneratedGlideModule) {
    if (sourceExecutor == null) {
      sourceExecutor = GlideExecutor.newSourceExecutor();
    }

    if (diskCacheExecutor == null) {
      diskCacheExecutor = GlideExecutor.newDiskCacheExecutor();
    }

    if (animationExecutor == null) {
      animationExecutor = GlideExecutor.newAnimationExecutor();
    }

    if (memorySizeCalculator == null) {
      memorySizeCalculator = new MemorySizeCalculator.Builder(context).build();
    }

    if (connectivityMonitorFactory == null) {
      connectivityMonitorFactory = new DefaultConnectivityMonitorFactory();
    }

    if (bitmapPool == null) {
      int size = memorySizeCalculator.getBitmapPoolSize();
      if (size > 0) {
        bitmapPool = new LruBitmapPool(size);
      } else {
        bitmapPool = new BitmapPoolAdapter();
      }
    }

    if (arrayPool == null) {
      arrayPool = new LruArrayPool(memorySizeCalculator.getArrayPoolSizeInBytes());
    }

    if (memoryCache == null) {
      memoryCache = new LruResourceCache(memorySizeCalculator.getMemoryCacheSize());
    }

    if (diskCacheFactory == null) {
      diskCacheFactory = new InternalCacheDiskCacheFactory(context);
    }

    if (engine == null) {
      engine =
          new Engine(
              memoryCache,
              diskCacheFactory,
              diskCacheExecutor,
              sourceExecutor,
              GlideExecutor.newUnlimitedSourceExecutor(),
              animationExecutor,
              isActiveResourceRetentionAllowed);
    }

    if (defaultRequestListeners == null) {
      defaultRequestListeners = Collections.emptyList();
    } else {
      defaultRequestListeners = Collections.unmodifiableList(defaultRequestListeners);
    }

    GlideExperiments experiments = glideExperimentsBuilder.build();
    RequestManagerRetriever requestManagerRetriever =
        new RequestManagerRetriever(requestManagerFactory, experiments);

    return new Glide(
        context,
        engine,
        memoryCache,
        bitmapPool,
        arrayPool,
        requestManagerRetriever,
        connectivityMonitorFactory,
        logLevel,
        defaultRequestOptionsFactory,
        defaultTransitionOptions,
        defaultRequestListeners,
        manifestModules,
        annotationGeneratedGlideModule,
        experiments);
  }

  static final class ManualOverrideHardwareBitmapMaxFdCount implements GlideExperiments.Experiment {

    final int fdCount;

    ManualOverrideHardwareBitmapMaxFdCount(int fdCount) {
      this.fdCount = fdCount;
    }
  }

  public static final class WaitForFramesAfterTrimMemory implements GlideExperiments.Experiment {
    private WaitForFramesAfterTrimMemory() {}
  }

  static final class EnableImageDecoderForBitmaps implements GlideExperiments.Experiment {}

  static final class EnableImageDecoderForAnimatedWebp implements GlideExperiments.Experiment {}

  /** See {@link #setLogRequestOrigins(boolean)}. */
  public static final class LogRequestOrigins implements GlideExperiments.Experiment {}

  static final class EnableLazyGlideRegistry implements GlideExperiments.Experiment {}
}
