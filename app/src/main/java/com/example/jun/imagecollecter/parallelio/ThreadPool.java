package com.example.jun.imagecollecter.parallelio;

import android.os.Handler;
import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by jun on 2018-04-09.
 * 싱글톤 출처 : http://superfelix.tistory.com/83
 */

public class ThreadPool {
    private static volatile ThreadPool instance = null;
    private ThreadPoolExecutor mThreadPoolExecutor = null;

    private ThreadPool(){}
    private void init(){
        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
        mThreadPoolExecutor = new ThreadPoolExecutor(
                NUMBER_OF_CORES * 2 + 1,
                NUMBER_OF_CORES * 2 + 1,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()
        );

        mThreadPoolExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r,
                                          ThreadPoolExecutor executor) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                executor.execute(r);
            }
        });
    }
    public static ThreadPool getInstance(){
        if(instance==null){
            synchronized (ThreadPoolExecutor.class){
                if(instance==null){
                    instance = new ThreadPool();
                }
            }
        }
        return instance;
    }

    public void execute(Runnable runnable){
        if(mThreadPoolExecutor == null){
            init();
        }
        mThreadPoolExecutor.execute(runnable);
    }
    public ThreadPoolExecutor get(){
        if(mThreadPoolExecutor == null){
            init();
        }
        return mThreadPoolExecutor;
    }

    public void stop(){
        if(mThreadPoolExecutor == null){
            return;
        }
        mThreadPoolExecutor.shutdown();
        try {
            if (!mThreadPoolExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                mThreadPoolExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            mThreadPoolExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}
