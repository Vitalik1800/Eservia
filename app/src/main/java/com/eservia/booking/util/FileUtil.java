package com.eservia.booking.util;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import androidx.core.content.FileProvider;

import com.eservia.booking.BuildConfig;

import java.io.File;
import java.util.Objects;

public class FileUtil {

    public static final String MEDIA_TYPE_PNG = "image/png";
    public static final String MEDIA_TYPE_JPEG = "image/jpeg";

    public static final int MAX_AVATAR_SIZE_KB = 5120;
    public static final int MIN_AVATAR_SIZE_KB = 6;

    public static long getFolderSizeKb(File f) {
        return getFolderSize(f) / 1024;
    }

    public static long getFolderSize(File f) {
        long size = 0;
        if (f.isDirectory()) {
            for (File file : Objects.requireNonNull(f.listFiles())) {
                size += getFolderSize(file);
            }
        } else {
            size = f.length();
        }
        return size;
    }

    public static String getMediaType(Context context, Uri imageUri) {
        return context.getContentResolver().getType(imageUri);
    }

    public static boolean isAvatarMediaTypeOk(String mediaType) {
        if (mediaType == null) {
            return false;
        }
        boolean isValid = false;
        if (mediaType.equals(MEDIA_TYPE_JPEG)) {
            isValid = true;
        } else if (mediaType.equals(MEDIA_TYPE_PNG)) {
            isValid = true;
        }
        return isValid;
    }

    public static Uri provideTempPhotoUri(Context context, File externalFile) {
        return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID
                + ".provider", externalFile);
    }

    /**
     * Provides temporal photo file url using content:// schema and FileProvider
     * <p>
     * NOTE: Starting from Android N (API 24) all apps targeting API 24 and higher should
     * not expose file:// uri, or FileUriExposedException will be thrown
     */
    public static File tempPhotoFile(String filename) {
        return new File(Environment.getExternalStorageDirectory() + File.separator + "."
                + filename);
    }
}
