package com.t2xm.utils.valuesConverter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;

import java.io.ByteArrayOutputStream;

public class ImageUtil {

    public static Bitmap imagePathToBitmap(String imagePath) {
        if (imagePath.equals("")) {
            return null;
        }
        return BitmapFactory.decodeFile(imagePath);
    }

    public static ByteArrayOutputStream getJpegBitmapOutputStream(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        return out;
    }

    public static byte[] outputStreamToByteArray(ByteArrayOutputStream out) {
        if (out == null) {
            return null;
        }
        return out.toByteArray();
    }

    public static byte[] imagePathToByteArray(String imagePath) {
        if (imagePath.equals("")) {
            return null;
        }
        return bitmapToByteArray(imagePathToBitmap(imagePath));
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream out = getJpegBitmapOutputStream(bitmap);
        return outputStreamToByteArray(out);
    }

    public static Bitmap byteArrayToBitmap(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static Bitmap compressBitmap(Bitmap bitmap) {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        final int MAX_SIZE = 512;
        if (height >= MAX_SIZE || width >= MAX_SIZE) {
            if (height >= width) {
                double scale = height / MAX_SIZE;
                return Bitmap.createScaledBitmap(bitmap, ((int) (width / scale)), ((int) (height / scale)), false);
            } else {
                double scale = width / MAX_SIZE;
                return Bitmap.createScaledBitmap(bitmap, ((int) (width / scale)), ((int) (height / scale)), false);
            }
        }
        return bitmap;
    }

    public static int getBitmapSize(Bitmap bitmap) {
        return bitmap.getAllocationByteCount();
    }
}
