package com.example.zhenfang.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.zhenfang.myapplication.database.dao.BaseInfoDao;
import com.example.zhenfang.myapplication.utils.BBAppLogger;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author zhangzf
 * @datetime 19 Sep 2014, 3:07 PM
 */
public abstract class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // private members
    private final DatabaseManager mDatabaseManager;

    public DatabaseHelper(Context context, String databaseName,DatabaseManager manager, int version) {
        super(context, databaseName, null, version);
        mDatabaseManager = manager;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            createTables();
        }
        catch(SQLException e){
            BBAppLogger.e("%s %s %s", DatabaseHelper.class.getName(),
                    "can't create com.example.zhenfang.myapplication.database", e);
            throw new RuntimeException(e);
        }
    }

    public void init() {
        getReadableDatabase();
    }

    private void createTables() throws SQLException {
        HashMap<String, BaseInfoDao> daoList = mDatabaseManager.getDaoMap();
        for (String key : daoList.keySet()) {
            Class ormBeanClass = daoList.get(key).getDBObjectClass();
            TableUtils.createTableIfNotExists(connectionSource, ormBeanClass);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        BBAppLogger.i("Database upgrade call back");
        if (newVersion > oldVersion) {
            UpgradeHelper helper = __createUpgradeHelper(database, oldVersion, newVersion);
            helper.runAllUpgrades();
        }
    }

    protected abstract UpgradeHelper __createUpgradeHelper(SQLiteDatabase database, int oldVersion, int newVersion);
}
