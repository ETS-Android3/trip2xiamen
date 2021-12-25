package com.t2xm.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionUtils {
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
}
