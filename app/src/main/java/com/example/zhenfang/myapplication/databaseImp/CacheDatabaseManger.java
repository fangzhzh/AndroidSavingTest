package com.example.zhenfang.myapplication.databaseImp;

import com.example.zhenfang.myapplication.application.CacheApplication;
import com.example.zhenfang.myapplication.database.DatabaseHelper;
import com.example.zhenfang.myapplication.database.DatabaseManager;
import com.example.zhenfang.myapplication.databaseImp.orm.dao.CacheDao;

/**
 * @author zhangzf
 * @since 6/22/15 4:36 PM
 */
public class CacheDatabaseManger extends DatabaseManager {
    private static CacheDatabaseManger __instance;
    static String DBCACHE_DAO = "DBCACHE_DAO";
    public static CacheDatabaseManger getInstance() {
        if (__instance == null) {
            __instance = new CacheDatabaseManger();
        }
        return __instance;
    }

    @Override
    protected String getDbPrefix() {
        return "cache";
    }

    @Override
    protected int __getUserId() {
        return 0;
    }

    @Override
    protected DatabaseHelper __createDatabaseHelper(String name, DatabaseManager databaseManager) {
        return new CacheDatabaseHelper(CacheApplication.getApplication().getApplicationContext(),
                name, databaseManager);
    }

    @Override
    protected void __initDao() {
        registerDao(DBCACHE_DAO, new CacheDao(mDBHelper));
    }

    public CacheDao getCacheDao() {
        return (CacheDao) getDao(DBCACHE_DAO);
    }

}
