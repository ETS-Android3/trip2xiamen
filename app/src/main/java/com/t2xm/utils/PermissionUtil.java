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
        return checkPermission(context, PermissionString.CAMERA);
    }

    public static boolean checkReadExternalStoragePermission(Context context){
        return checkPermission(context, PermissionString.READ_EXTERNAL_STORAGE);
    }

    public static boolean checkWriteExternalStoragePermission(Context context){
        return checkPermission(context, PermissionString.WRITE_EXTERNAL_STORAGE);
    }

    public static void grantCameraPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{PermissionString.CAMERA}, RequestCode.CAMERA);
    }

    public static void grantReadExternalStoragePermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{PermissionString.READ_EXTERNAL_STORAGE}, RequestCode.READ_EXTERNAL_STORAGE);
    }

    public static void grantWriteExternalStoragePermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{PermissionString.WRITE_EXTERNAL_STORAGE}, RequestCode.WRITE_EXTERNAL_STORAGE);
    }
}
