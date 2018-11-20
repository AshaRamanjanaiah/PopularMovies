package com.example.android.popularmoviesapp.NetworkUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {

    private static final Object LOCK = new Object();
    private Executor diskIO;
    private static AppExecutor sInstance;

    public AppExecutor(Executor diskIO){
        this.diskIO = diskIO;
    }

    public static AppExecutor getInstance(){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new AppExecutor(Executors.newSingleThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor diskIO(){return diskIO;}
}
