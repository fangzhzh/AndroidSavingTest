package com.example.zhenfang.myapplication.database.dao;

import com.example.zhenfang.myapplication.database.DatabaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;

/**
 * @author amulya
 * @datetime 06 Mar 2014, 6:11 PM
 */
public abstract class BaseInfoDao<D, T> {

    private Dao<D, T> mDao;

    private final DatabaseHelper mDBHelper;

    private Class<D> mClass;

    public BaseInfoDao(DatabaseHelper helper, Class<D> clazz) {
        mDBHelper = helper;
        mClass = clazz;
    }

    protected DatabaseHelper getHelper() {
        return mDBHelper;
    }

    protected Dao<D, T> getDao() throws SQLException {
        if (mDao == null) {
            mDao = DaoManager.createDao(getHelper().getConnectionSource(), getDBObjectClass());
            mDao.setObjectCache(true);
        }
        return mDao;
    }

    public Class getDBObjectClass() {
        return mClass;
    }

    public void clearCache() {
        if (mDao != null) {
            mDao.clearObjectCache();
        }
    }

}
