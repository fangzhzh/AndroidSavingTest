package com.example.zhenfang.myapplication.utils;

import android.util.LruCache;

import com.example.zhenfang.myapplication.application.CacheApplication;
import com.garena.android.fs.Storage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangzf
 * @since 6/24/15 12:14 PM
 */
public class LRUSharePreferenceCache {

    private PrefLRUCache mCache;
    private Map<String, String> mMap;
    private Storage mStorage;
    private int writeCount = 0;

    private Runnable writeFile = new Runnable() {
        @Override
        public void run() {
            if (writeCount > 5) {
                writeCacheToFile();
                writeCount = 0;
            }
        }
    };


    private final ScheduledExecutorService scheduledExecutorService =
            Executors.newScheduledThreadPool(1);

    public LRUSharePreferenceCache(int maxSize) {
        mCache = new PrefLRUCache(maxSize);
        mMap = new HashMap<>();
        mStorage = Storage.with(CacheApplication.getApplication().getApplicationContext());
        scheduledExecutorService.scheduleAtFixedRate(writeFile, 1, 1, TimeUnit.MINUTES);
        syncCacheFromFile();
    }

    public void put(String key, String value) {
        mMap.put(key, value);
        mCache.put(key, value);
        ++writeCount;
    }

    public String get(String key) {
        return mCache.get(key);
    }

    private void syncCacheFromFile() {
        try {
            byte[] storage = mStorage.syncRead("filename");
            if (storage == null) {
                return;
            }
            JSONArray jArray = new JSONArray(new String(storage));
            for (int i = 0; i < jArray.length(); ++i) {
                JSONObject jObject = (JSONObject) jArray.get(i);
                Iterator<?> keys = jObject.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    mCache.put(key, (String) jObject.get(key));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void writeCacheToFile() {
        JSONArray array = new JSONArray();
        for (Map.Entry<String, String> entry : mMap.entrySet()) {
            try {
                array.put(new JSONObject()
                        .put(entry.getKey(), entry.getValue())
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        mStorage.syncWrite("filename", array.toString().getBytes(), true);
    }

    private class PrefLRUCache extends LruCache<String, String> {

        /**
         * @param maxSize for caches that do not override {@link #sizeOf}, this is
         *                the maximum number of entries in the cache. For all other caches,
         *                this is the maximum sum of the sizes of the entries in this cache.
         */
        public PrefLRUCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected void entryRemoved(boolean evicted, String key, String oldValue, String newValue) {
            super.entryRemoved(evicted, key, oldValue, newValue);
            mMap.remove(key);
        }

    }
}
