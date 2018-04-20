package com.example.jun.imagecollecter.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jun on 2018-04-09.
 */

class CacheUtil {

    static void storeBitmap(String directory, Bitmap bitmap, String name) throws IOException {
        if(directory == null || bitmap == null){
            return;
        }
        File file = new File(directory, name);
        FileOutputStream os = new FileOutputStream(file);// = new FileOutputStream(file);

        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        } finally {
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static Bitmap readBitmap(File file) {

        if (!file.exists()) {
            return null;
        }
        Bitmap bitmap= null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    static Bitmap readBitmap(String directory, String name) {
        if(directory == null){
            return null;
        }
        File file = new File(directory, name);
        return readBitmap(file);
    }
}
