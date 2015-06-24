package com.example.zhenfang.myapplication.application;

import android.app.Application;
import android.content.Context;

import com.example.zhenfang.myapplication.databaseImp.store.CacheStore;

/**
 * @author zhangzf
 * @since 6/22/15 3:46 PM
 */
public class CacheApplication extends Application {
    private static Application mInstance;
    private CacheStore cacheStore;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        cacheStore = new CacheStore(getSharedPreferences("cache", MODE_PRIVATE));
    }

    public CacheStore getCacheStore() {
        return cacheStore;
    }

    public static Context getApplication() {
        return mInstance;
    }

    public static CacheApplication get() {
        return (CacheApplication) mInstance;
    }
}
