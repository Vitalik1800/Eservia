package com.eservia.glide.load.resource.bitmap;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.eservia.glide.Glide;
import com.eservia.glide.load.Transformation;
import com.eservia.glide.load.engine.Resource;
import com.eservia.glide.load.engine.bitmap_recycle.BitmapPool;
import com.eservia.glide.request.target.Target;
import com.eservia.glide.util.Util;


public abstract class BitmapTransformation implements Transformation<Bitmap> {

  @NonNull
  @Override
  public final Resource<Bitmap> transform(
      @NonNull Context context, @NonNull Resource<Bitmap> resource, int outWidth, int outHeight) {
    if (!Util.isValidDimensions(outWidth, outHeight)) {
      throw new IllegalArgumentException(
          "Cannot apply transformation on width: "
              + outWidth
              + " or height: "
              + outHeight
              + " less than or equal to zero and not Target.SIZE_ORIGINAL");
    }
    BitmapPool bitmapPool = Glide.get(context).getBitmapPool();
    Bitmap toTransform = resource.get();
    int targetWidth = outWidth == Target.SIZE_ORIGINAL ? toTransform.getWidth() : outWidth;
    int targetHeight = outHeight == Target.SIZE_ORIGINAL ? toTransform.getHeight() : outHeight;
    Bitmap transformed = transform(bitmapPool, toTransform, targetWidth, targetHeight);

    final Resource<Bitmap> result;
    if (toTransform.equals(transformed)) {
      result = resource;
    } else {
      result = BitmapResource.obtain(transformed, bitmapPool);
    }
    return result;
  }

  public void setCanvasBitmapDensity(@NonNull Bitmap toTransform, @NonNull Bitmap canvasBitmap) {
    canvasBitmap.setDensity(toTransform.getDensity());
  }

    public abstract Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool,
                                     @NonNull Bitmap toTransform, int outWidth, int outHeight);

    protected abstract Bitmap transform(
      @NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight);
}
