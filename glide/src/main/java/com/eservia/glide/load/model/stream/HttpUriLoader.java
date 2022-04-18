package com.eservia.glide.load.model.stream;

import android.net.Uri;
import com.eservia.glide.load.model.GlideUrl;
import com.eservia.glide.load.model.ModelLoader;
import com.eservia.glide.load.model.UrlUriLoader;
import java.io.InputStream;

/**
 * Loads {@link InputStream}s from http or https {@link Uri}s.
 *
 * @deprecated Use {@link UrlUriLoader} instead
 */
@Deprecated
public class HttpUriLoader extends UrlUriLoader<InputStream> {

  // Public API.
  @SuppressWarnings("WeakerAccess")
  public HttpUriLoader(ModelLoader<GlideUrl, InputStream> urlLoader) {
    super(urlLoader);
  }

  /**
   * Factory for loading {@link InputStream}s from http/https {@link Uri}s.
   *
   * @deprecated Use {@link StreamFactory} instead
   */
  @Deprecated
  public static class Factory extends StreamFactory {
    // Defer to StreamFactory's implementation
  }
}
