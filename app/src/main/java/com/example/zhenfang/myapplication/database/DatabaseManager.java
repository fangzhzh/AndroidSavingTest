package com.example.zhenfang.myapplication.database;

import android.support.annotation.Nullable;

import com.example.zhenfang.myapplication.database.dao.BaseInfoDao;
import com.j256.ormlite.dao.DaoManager;

import java.util.HashMap;
import java.util.Locale;

/**
 * @author zhangzf
 * @datetime 22 Dec 2014, 6:11 PM
 */

public abstract class DatabaseManager {
    private int mUserId = -1;
    protected DatabaseHelper mDBHelper;
    private HashMap<String, BaseInfoDao> mDaoList;

    protected abstract String getDbPrefix();

    public HashMap<String, BaseInfoDao> getDaoMap() {
        return mDaoList;
    }

    protected DatabaseManager() {
        initDB();
    }

    protected abstract int __getUserId();

    private void initDB() {
        // lets open the com.example.zhenfang.myapplication.database and read it.
        int currentUser = __getUserId();

        // if the current user is logged out or already same as current DB user, simply return
        if (currentUser < 0 || mUserId == currentUser) {
            return;
        }

        mUserId = currentUser;
        mDBHelper = __createDatabaseHelper(
                String.format(Locale.ENGLISH, getDbPrefix() + "%d.db", mUserId), this);

        // let us initialize the application DAO objects
        initDao();
        // only now init the helper.
        mDBHelper.init();
    }

    protected abstract DatabaseHelper __createDatabaseHelper(String format, DatabaseManager databaseManager);

    private void initDao() {
        mDaoList = new HashMap<>();
        __initDao();
    }

    protected abstract void __initDao();

    public void registerDao(String daoName, BaseInfoDao dao) {
        mDaoList.put(daoName, dao);
    }

    @Nullable
    protected BaseInfoDao getDao(String daoName) {
        return getDaoMap().get(daoName);
    }

    public void clearCache() {
        mUserId = -1;
        if (mDBHelper == null) {
            return;
        }

        for (BaseInfoDao dao : mDaoList.values()) {
            dao.clearCache();
        }
        mDaoList.clear();
        mDBHelper.close();

        DaoManager.clearCache();
        DaoManager.clearDaoCache();
    }


}
