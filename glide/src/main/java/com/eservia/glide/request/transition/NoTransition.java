package com.eservia.glide.request.transition;

import com.eservia.glide.load.DataSource;
import com.eservia.glide.util.Synthetic;


public class NoTransition<R> implements Transition<R> {
  @Synthetic static final NoTransition<?> NO_ANIMATION = new NoTransition<>();

  @SuppressWarnings("rawtypes")
  private static final TransitionFactory<?> NO_ANIMATION_FACTORY = new NoAnimationFactory();

  public static class NoAnimationFactory<R> implements TransitionFactory<R> {
    @SuppressWarnings("unchecked")
    @Override
    public Transition<R> build(DataSource dataSource, boolean isFirstResource) {
      return (Transition<R>) NO_ANIMATION;
    }
  }

  /** Returns an instance of a factory that produces {@link NoTransition}s. */
  @SuppressWarnings("unchecked")
  public static <R> TransitionFactory<R> getFactory() {
    return (TransitionFactory<R>) NO_ANIMATION_FACTORY;
  }

  /** Returns an instance of {@link NoTransition}. */
  @SuppressWarnings("unchecked")
  public static <R> Transition<R> get() {
    return (Transition<R>) NO_ANIMATION;
  }

  /** Performs no animation and always returns {@code false}. */
  @Override
  public boolean transition(Object current, ViewAdapter adapter) {
    return false;
  }
}
