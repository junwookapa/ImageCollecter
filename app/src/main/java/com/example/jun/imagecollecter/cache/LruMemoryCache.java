package com.example.jun.imagecollecter.cache;

import android.graphics.Bitmap;
import android.os.HandlerThread;
import android.support.v4.util.LruCache;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by jun on 2018-04-09.
 * 1. sizeOf : 각 엔트리의 사이즈를 의미 default는 1
 *
 *
 */

class LruMemoryCache{

    private LruCache<String, Bitmap> mMemoryCache;

    LruMemoryCache(int kbyte){
        mMemoryCache = new LruCache<String, Bitmap>(kbyte){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    void cache(String key, Bitmap bitmap) {
        if(bitmap == null){
            return;
        }
        mMemoryCache.put(key, bitmap);
    }

    Bitmap get(String key) {
        return mMemoryCache.get(key);
    }

    boolean isExist(String key) {
        return mMemoryCache.get(key) != null;
    }


}
