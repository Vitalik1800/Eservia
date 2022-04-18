package com.eservia.glide.module;

import android.content.Context;
import androidx.annotation.NonNull;
import com.eservia.glide.GlideBuilder;

@Deprecated
interface AppliesOptions {

  void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder);
}
