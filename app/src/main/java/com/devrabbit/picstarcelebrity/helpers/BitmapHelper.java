package com.devrabbit.picstarcelebrity.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class BitmapHelper {
    public static Bitmap decodeBitmapFromPath(String imagePath, int reqWidth, int reqHeight, int orientation) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        BitmapFactory.decodeFile(imagePath, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        System.err.println(imagePath);
        System.err.println("Calculated inSampleSize = " + options.inSampleSize);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bmp = BitmapFactory.decodeFile(imagePath, options);
        if (orientation != 0) {
            return getRotatedBitmap(bmp, orientation);
        }
        if (bmp == null) {
            System.err.println("Returning NULL");
        }
        return bmp;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }
    private static Bitmap getRotatedBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.setRotate(angle, 0, 0);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }


    public static Bitmap getResizedBitmap(Bitmap source, int newWidth, int newHeight) throws NullPointerException {
        if (source != null) {
            int width = source.getWidth();
            int height = source.getHeight();
            // create a matrix for the manipulation
            Matrix matrix = new Matrix();
            // resize the bitmap
            matrix.postScale(((float) newWidth) / width, ((float) newHeight) / height);
            // recreate the new Bitmap
            return Bitmap.createBitmap(source, 0, 0, width, height, matrix, true);
        } else {
            throw new NullPointerException("Inputted Bitmap is NULL");
        }
    }
}
