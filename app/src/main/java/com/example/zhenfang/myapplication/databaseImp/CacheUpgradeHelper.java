package com.example.zhenfang.myapplication.databaseImp;

import android.database.sqlite.SQLiteDatabase;

import com.example.zhenfang.myapplication.database.UpgradeHelper;
import com.example.zhenfang.myapplication.database.UpgradeScript;

import java.util.List;

/**
 * @author zhangzf
 * @since 6/22/15 4:38 PM
 */
public class CacheUpgradeHelper extends UpgradeHelper {
    public CacheUpgradeHelper(SQLiteDatabase database, int oldVersion, int newVersion) {
        super(database, oldVersion, newVersion);
    }

    @Override
    protected List<UpgradeScript> getAllUpgradeScripts() {
        return null;
    }
}
