package com.t2xm.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionUtil {
    public static boolean checkPermission(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkCameraPermission(Context context) {
        return checkPermission(context, Manifest.permission.CAMERA);
    }

    public static boolean checkReadExternalStoragePermission(Context context){
        return checkPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public static boolean checkWriteExternalStoragePermission(Context context){
        return checkPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public static void grantCameraPermission(Activity activity) {
        //TODO add request code to static class
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 0);
    }
}
