package com.t2xm.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static Toast createToast(Context context, String message) {
        return Toast.makeText(context, message, Toast.LENGTH_SHORT);
    }

    public static void createAndShowToast(Context context, String message) {
        createToast(context, message).show();
    }
}
