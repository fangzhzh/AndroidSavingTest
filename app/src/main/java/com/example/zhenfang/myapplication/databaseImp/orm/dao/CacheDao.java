package com.example.zhenfang.myapplication.databaseImp.orm.dao;

import android.support.annotation.Nullable;

import com.example.zhenfang.myapplication.database.DatabaseHelper;
import com.example.zhenfang.myapplication.database.dao.BaseInfoDao;
import com.example.zhenfang.myapplication.databaseImp.orm.bean.DBCache;
import com.example.zhenfang.myapplication.utils.BBAppLogger;
import com.j256.ormlite.dao.Dao;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author zhangzf
 * @since 6/22/15 5:14 PM
 */
public class CacheDao extends BaseInfoDao<DBCache, String>{
    public CacheDao(DatabaseHelper helper) {
        super(helper, DBCache.class);
    }


    public void save(DBCache dbCache) {
        try {
            final Dao<DBCache, String> dao = getDao();
            dao.createOrUpdate(dbCache);
        } catch (Exception e) {
            BBAppLogger.e(e);
        }
    }

    @Nullable
    public DBCache get(String key) {
        try {
            final Dao<DBCache, String> dao = getDao();
            List<DBCache> cacheList = dao.queryForEq(DBCache.COLUMN.KEY, key);
            if (cacheList != null && !cacheList.isEmpty()) {
                return cacheList.get(0);
            }
        } catch (Exception e) {
            BBAppLogger.e(e);
        }
        return null;
    }

    public void save(final List<DBCache> keyValueList) {
        try {
            final Dao<DBCache, String> dao = getDao();
            dao.callBatchTasks( new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    for (DBCache dbCache : keyValueList) {
                        dao.createOrUpdate(dbCache);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            BBAppLogger.e(e);
        }
    }
}
