package com.eservia.glide.manager;

import android.content.Context;
import androidx.annotation.NonNull;


public interface ConnectivityMonitorFactory {

  @NonNull
  ConnectivityMonitor build(
      @NonNull Context context, @NonNull ConnectivityMonitor.ConnectivityListener listener);
}
