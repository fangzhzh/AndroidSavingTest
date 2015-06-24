package com.example.zhenfang.myapplication.databaseImp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.zhenfang.myapplication.database.DatabaseHelper;
import com.example.zhenfang.myapplication.database.DatabaseManager;
import com.example.zhenfang.myapplication.database.UpgradeHelper;

/**
 * @author zhangzf
 * @since 6/22/15 4:37 PM
 */
public class CacheDatabaseHelper extends DatabaseHelper {
    private static final int DATABASE_VERSION = 6;

    public CacheDatabaseHelper(Context context, String databaseName, DatabaseManager manager) {
        super(context, databaseName, manager, DATABASE_VERSION);
    }

    @Override
    protected UpgradeHelper __createUpgradeHelper(SQLiteDatabase database, int oldVersion,
                                                  int newVersion) {
        return new CacheUpgradeHelper(database, oldVersion, newVersion);
    }
}
