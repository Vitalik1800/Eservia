package com.eservia.glide.signature;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import com.eservia.glide.load.Key;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class ApplicationVersionSignature {
  private static final String TAG = "AppVersionSignature";
  private static final ConcurrentMap<String, Key> PACKAGE_NAME_TO_KEY = new ConcurrentHashMap<>();


  @NonNull
  public static Key obtain(@NonNull Context context) {
    String packageName = context.getPackageName();
    Key result = PACKAGE_NAME_TO_KEY.get(packageName);
    if (result == null) {
      Key toAdd = obtainVersionSignature(context);
      result = PACKAGE_NAME_TO_KEY.putIfAbsent(packageName, toAdd);
      // There wasn't a previous mapping, so toAdd is now the Key.
      if (result == null) {
        result = toAdd;
      }
    }

    return result;
  }

  @VisibleForTesting
  static void reset() {
    PACKAGE_NAME_TO_KEY.clear();
  }

  @NonNull
  private static Key obtainVersionSignature(@NonNull Context context) {
    PackageInfo packageInfo = getPackageInfo(context);
    String versionCode = getVersionCode(packageInfo);
    return new ObjectKey(versionCode);
  }

  @NonNull
  private static String getVersionCode(@Nullable PackageInfo packageInfo) {
    String versionCode;
    if (packageInfo != null) {
      versionCode = String.valueOf(packageInfo.versionCode);
    } else {
      versionCode = UUID.randomUUID().toString();
    }
    return versionCode;
  }

  @Nullable
  private static PackageInfo getPackageInfo(@NonNull Context context) {
    try {
      return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  private ApplicationVersionSignature() {
    // Empty for visibility.
  }
}
