package com.t2xm.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.t2xm.utils.values.PermissionString;
import com.t2xm.utils.values.RequestCode;

public class PermissionUtil {
    public static boolean permissionGranted(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean cameraPermissionGranted(Context context) {
        return permissionGranted(context, PermissionString.CAMERA);
    }

    public static boolean readExternalStoragePermissionGranted(Context context) {
        return permissionGranted(context, PermissionString.READ_EXTERNAL_STORAGE);
    }

    public static boolean writeExternalStoragePermissionGranted(Context context) {
        return permissionGranted(context, PermissionString.WRITE_EXTERNAL_STORAGE);
    }

    public static boolean phoneCallPermissionGranted(Context context) {
        return permissionGranted(context, PermissionString.CALL_PHONE);
    }

    public static void grantCameraPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{PermissionString.CAMERA}, RequestCode.USE_CAMERA_PERMISSION);
    }

    public static void grantReadExternalStoragePermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{PermissionString.READ_EXTERNAL_STORAGE},
                RequestCode.READ_EXTERNAL_STORAGE_PERMISSION);
    }

    public static void grantWriteExternalStoragePermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{PermissionString.WRITE_EXTERNAL_STORAGE}, RequestCode.WRITE_EXTERNAL_STORAGE);
    }

    public static void grantCallPhonePermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{PermissionString.CALL_PHONE}, RequestCode.CALL_PHONE_PERMISSION);
    }
}
