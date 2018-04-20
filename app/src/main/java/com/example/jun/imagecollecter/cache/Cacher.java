package com.example.jun.imagecollecter.cache;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.example.jun.imagecollecter.Common;
import com.example.jun.imagecollecter.parallelio.ParallelIOUtil;
import com.example.jun.imagecollecter.parallelio.ThreadPool;

import java.util.Date;


/**
 * Created by jun on 2018-04-09.
 * 참고
 * Lru : http://egloos.zum.com/judoboyjin/v/4545185
 * 안드로이드 캐시 1: http://javacan.tistory.com/entry/android-file-cache-implementation
 * 안드로이드 캐시 2: https://github.com/JakeWharton/DiskLruCache
 *
 */

public class Cacher {

    private LruMemoryCache mLruMemoryCache;
    private LruDiskCache mLruDiskCache;
    public Cacher(Context context){
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int memoryCacheSize = maxMemory / 8; //kb
        final long diskCacheSize = 20 * 1024; // 10 kb * 1024 = 10mb

        mLruMemoryCache = new LruMemoryCache(memoryCacheSize);
        mLruDiskCache = new LruDiskCache(context.getCacheDir().getAbsolutePath(), diskCacheSize);
    }

    public Bitmap get(String key){
        Bitmap bitmap = mLruMemoryCache.get(key);
        if(bitmap != null){
            return bitmap;
        }
        bitmap = mLruDiskCache.get(key);
        return bitmap;
    }

    public void cache(final String key, final Bitmap bitmap){
        mLruMemoryCache.cache(key, bitmap);
        ThreadPool.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                mLruDiskCache.cache(key, bitmap);
            }
        });

    }

    public boolean isExist(String key){
        return mLruMemoryCache.isExist(key) || mLruDiskCache.isExist(key);
    }
    
}
