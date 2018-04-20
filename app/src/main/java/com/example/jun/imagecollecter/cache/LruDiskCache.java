package com.example.jun.imagecollecter.cache;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by jun on 2018-04-09.
 */

class LruDiskCache {
    private Map<String, File> mLruMap = new LinkedHashMap<>(0, 0.75f, true);

    private final String CACHE_ROOT;
    private final long MAX_CACHE_KB_SIZE;
    private long mCurrentSize;

    LruDiskCache(String filePath, long kbyte){
        CACHE_ROOT = filePath;
        MAX_CACHE_KB_SIZE = kbyte;
        try {
            File[] cachedFiles = new File(filePath).listFiles();
            Arrays.sort(cachedFiles, new FileDateAscCompare());

            for (File file : cachedFiles) {
                mLruMap.put(file.getName(), file);
                mCurrentSize += file.length() / 1024; // kbyte
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void cache(String key, Bitmap bitmap) {
        if(isExist(key)){
            update(key);
        }else{
            File file = new File(CACHE_ROOT);
            mLruMap.put(key, file);
            try {
                CacheUtil.storeBitmap(CACHE_ROOT, bitmap, key);
                checkMaxCacheSizeAndCleanUp();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    Bitmap get(String key) {
        if(isExist(key)){
            return CacheUtil.readBitmap(mLruMap.get(key));
        }else {
            return null;
        }

    }

    boolean isExist(String key) {
        return mLruMap.containsKey(key);
    }

    private void update(String key){
        File file = mLruMap.get(key);
        mLruMap.remove(key);
        mLruMap.put(key, file);
    }

    private void checkMaxCacheSizeAndCleanUp(){
        Iterator<String> iter = mLruMap.keySet().iterator();
        while (mCurrentSize >= MAX_CACHE_KB_SIZE && iter.hasNext()){
            String key = iter.next();
            File file = mLruMap.get(key);
            long size = file.length() / 1024;
            if(file.exists()){
                file.delete();
            }
            mLruMap.remove(key);
            mCurrentSize -= size;
        }
    }

    static class FileDateAscCompare implements Comparator<File> {

        @Override
        public int compare(File arg0, File arg1) {
            return (int)(arg0.lastModified() - arg1.lastModified());
        }

    }
}
