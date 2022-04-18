package com.eservia.glide.request;

import com.eservia.glide.request.target.Target;
import java.util.concurrent.Future;

public interface FutureTarget<R> extends Future<R>, Target<R> {}
