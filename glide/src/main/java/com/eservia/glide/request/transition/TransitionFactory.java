package com.eservia.glide.request.transition;

import com.eservia.glide.load.DataSource;

/**
 * A factory class that can produce different {@link Transition}s based on the state of the request.
 *
 * @param <R> The type of resource that needs to be animated into the target.
 */
public interface TransitionFactory<R> {


  Transition<R> build(DataSource dataSource, boolean isFirstResource);
}
