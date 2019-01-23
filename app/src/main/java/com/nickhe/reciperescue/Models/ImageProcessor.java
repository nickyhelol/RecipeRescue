package com.nickhe.reciperescue.Models;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.IOException;

public class ImageProcessor {

    public static Bitmap convertUriToBitmap(Activity context, Uri uri)
    {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);

            return bitmap;
        } catch (IOException e) {
            System.err.println("Converting Uri to Bitmap unsuccessfully!");

            return null;
        }
    }
}
