package com.eservia.booking.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

public class LocationUtil {

    public static boolean isFineLocationPermissionGranted(Context context) {
       return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
               == PackageManager.PERMISSION_GRANTED
               && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
               == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean shouldShowFineLocationRationale(Activity activity) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public static void requestFineLocationPermission(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                requestCode);
    }

    public static boolean permissionWasGranted(int[] grantResults) {
        return grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }

    public static class LocationCallbackImpl extends LocationCallback {

        private final LocationCallbackImplListener mListener;

        public LocationCallbackImpl(LocationCallbackImplListener listener) {
            mListener = listener;
        }

        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (mListener != null) {
                mListener.onLocationResult(locationResult);
            }
        }

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            if (mListener != null) {
                mListener.onLocationAvailability(locationAvailability);
            }
        }
    }

    public interface LocationCallbackImplListener {

        void onLocationResult(LocationResult locationResult);

        void onLocationAvailability(LocationAvailability locationAvailability);
    }
}
