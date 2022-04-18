package com.eservia.glide.request;

import com.eservia.glide.load.DataSource;
import com.eservia.glide.request.target.Target;

/**
 * An extension of {@link RequestListener} with additional parameters.
 *
 * <p>All equivalent methods are called at the relevant time by Glide. Implementations therefore
 * should only implement one version of each method.
 *
 * @param <ResourceT> The type of resource that will be loaded for the request.
 * @deprecated Not ready for public consumption, avoid using this class. It may be removed at any
 *     time.
 */
@Deprecated
public abstract class ExperimentalRequestListener<ResourceT> implements RequestListener<ResourceT> {

  public void onRequestStarted(Object model) {}


  public abstract boolean onResourceReady(
      ResourceT resource,
      Object model,
      Target<ResourceT> target,
      DataSource dataSource,
      boolean isFirstResource,
      boolean isAlternateCacheKey);
}
