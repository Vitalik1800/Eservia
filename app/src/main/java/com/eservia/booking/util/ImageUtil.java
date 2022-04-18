package com.eservia.booking.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.eservia.glide.Glide;
import com.eservia.glide.MaskTransformation;
import com.eservia.glide.load.MultiTransformation;
import com.eservia.glide.load.engine.DiskCacheStrategy;
import com.eservia.glide.load.resource.bitmap.CenterCrop;
import com.eservia.glide.load.resource.drawable.DrawableTransitionOptions;
import com.eservia.glide.request.RequestOptions;
import com.eservia.model.remote.UrlList;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ImageUtil {

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        String result;
        Cursor cursor = context.getContentResolver().query(
                contentUri, null, null, null, null);
        if (cursor == null) {
            result = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static File getFileFromUri(Context context, Uri contentUri) {
        String imageRealPath = ImageUtil.getRealPathFromUri(context, contentUri);
        return new File(imageRealPath);
    }

    public static MultipartBody.Part createImageRequestBody(File file, String mediaType) {
        RequestBody body = RequestBody.create(MediaType.parse(mediaType), file);
        return MultipartBody.Part.createFormData("file", file.getName(), body);
    }

    public static String getUserPhotoPath(String photoSize, String photoId) {
        return addSlashToPath(UrlList.getUsersApiImage()) + getPathWithPhotoSize(photoSize, photoId);
    }

    public static String addSlashToPath(String path) {
        if (path.endsWith("/")) {
            return path;
        } else {
            return path + "/";
        }
    }

    private static String getPathWithPhotoSize(String photoSize, String photoId) {
        if (photoSize.isEmpty()) {
            return photoId;
        } else {
            return photoSize + "/" + photoId;
        }
    }

    public static void displayBusinessImageTransform(Context context, ImageView view,
                                                     String imageLink, @DrawableRes int defaultImage,
                                                     @DrawableRes int mask) throws ClassNotFoundException {
        Glide.with(context)
                .load(imageLink)
                .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(),
                        new MaskTransformation(mask))))
                .apply(RequestOptions.placeholderOf(defaultImage))
                .apply(RequestOptions.errorOf(defaultImage))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view);
    }

    public static void displayStaffImageRound(Context context, ImageView view,
                                              String imageLink, @DrawableRes Integer defaultImage) throws ClassNotFoundException {
        Glide.with(context)
                .load(imageLink)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.placeholderOf(defaultImage))
                .apply(RequestOptions.errorOf(defaultImage))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view);
    }

}
