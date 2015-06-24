package com.example.zhenfang.myapplication.databaseImp.store;

import android.content.SharedPreferences;

import com.example.zhenfang.myapplication.application.CacheApplication;
import com.example.zhenfang.myapplication.databaseImp.CacheDatabaseManger;
import com.example.zhenfang.myapplication.databaseImp.orm.bean.DBCache;
import com.example.zhenfang.myapplication.databaseImp.orm.builder.DBCacheBuilder;
import com.example.zhenfang.myapplication.databaseImp.orm.dao.CacheDao;
import com.example.zhenfang.myapplication.utils.BBAppLogger;
import com.example.zhenfang.myapplication.utils.LRUSharePreferenceCache;
import com.example.zhenfang.myapplication.utils.SimpleDiskCache;
import com.garena.android.fs.Storage;
import com.tale.prettysharedpreferences.PrettySharedPreferences;
import com.tale.prettysharedpreferences.StringEditor;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author zhangzf
 * @since 6/22/15 3:41 PM
 */
public class CacheStore extends PrettySharedPreferences<CacheStore> {
    CacheDao dao = CacheDatabaseManger.getInstance().getCacheDao();
    private SimpleDiskCache mDiskCache;
    private Storage mStorage;
    LRUSharePreferenceCache lru;
    public CacheStore(SharedPreferences sharedPreferences) {
        super(sharedPreferences);
        try {
            File dir =  CacheApplication.getApplication().getApplicationContext().getFilesDir();
            mDiskCache = SimpleDiskCache.open(dir, 0, (long)(100 * Math.pow(2, 20)));
            mStorage = Storage.with(CacheApplication.getApplication().getApplicationContext());
            lru = new LRUSharePreferenceCache(20*1024*1024);
        } catch (IOException e) {
            BBAppLogger.e(e);
        }
    }

    public StringEditor<CacheStore> String(String key) {
        return getStringEditor(key);
    }

    public void save(String key, String value) {
        DBCacheBuilder builder = DBCacheBuilder.aDBCache();
        builder.withKey(key).withValue(value).build();
        dao.save(builder.build());
    }

    public DBCache getDb(String key) {
        return dao.get(key);
    }

    public static DBCache composeCache(String key, String value) {
        DBCacheBuilder builder = DBCacheBuilder.aDBCache();
        return builder.withKey(key).withValue(value).build();
    }

    public void save(List<DBCache> keyValueList) {
        dao.save(keyValueList);
    }

    public void put(String key, String data) {
        try {
            mDiskCache.put(key, data);
        } catch (IOException e) {
            BBAppLogger.e(e);
        }
    }

    public String getFromRu(String key) {
        try {
            SimpleDiskCache.StringEntry entry = mDiskCache.getString(key);
            if (entry != null) {
                return entry.getString();
            }
        } catch (IOException e) {
            BBAppLogger.e(e);
        }
        return null;
    }

    public void storeSave(String key, String value) {
        mStorage.syncWritePrivateCache(key, value.getBytes(), true);
    }

    public String getStoreValue(String key) {
        byte[] data = mStorage.syncReadPrivateCache(key);
        if (data != null) {
            return new String(data);
        }
        return null;
    }

    public void LRUSave(String key, String value) {
        lru.put(key, value);
    }

    public String LURGet(String key) {
        return lru.get(key);
    }



}
