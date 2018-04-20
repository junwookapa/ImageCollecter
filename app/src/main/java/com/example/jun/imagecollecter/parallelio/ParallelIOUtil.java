package com.example.jun.imagecollecter.parallelio;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jun on 2018-04-10.
 */

public class ParallelIOUtil {

    public static Bitmap downloadBitmap(String url) {
        Bitmap bitmap = null;
        try {
            InputStream input = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(input);
            Map g = new HashMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
