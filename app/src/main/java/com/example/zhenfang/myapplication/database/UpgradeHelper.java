package com.example.zhenfang.myapplication.database;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author amulya
 * @datetime 10 Jul 2014, 4:32 PM
 */
public abstract class UpgradeHelper {

    private int oldVersion;
    private int newVersion;
    private SQLiteDatabase databaseDao;

    private ArrayList<UpgradeScript> scripts;

    public UpgradeHelper(SQLiteDatabase database, int oldVersion, int newVersion) {
        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
        this.databaseDao = database;
        scripts = new ArrayList<>();
    }

    /**
     * Add your upgrade script to the script queue. NOTE:ORDER MATTERS!!
     * So only add to the last line.
     */
    void loadScripts() {
        scripts.addAll(getAllUpgradeScripts());
    }

    protected abstract List<UpgradeScript> getAllUpgradeScripts();

    /**
     * This method should calls, each upgrade script in the queue and
     * runs it if it is appropriate.
     */
    public void runAllUpgrades() {
        loadScripts();
        try {
            for (UpgradeScript script : scripts) {
                // if script is required for this version
                if (script.shouldRunScript(oldVersion, newVersion)) {
                    // split statements of the SQL, then execute one by one.
                    String[] statements = script.getScriptSQL().split(";");
                    for (String statement : statements) {
                        if (TextUtils.isEmpty(statement)) {
                            continue;
                        }
                        databaseDao.execSQL(statement);
                    }
                }
            }
        } catch (SQLException e) {
            //still throw the exception to engage db rollback
            throw e;
        }
    }


}

