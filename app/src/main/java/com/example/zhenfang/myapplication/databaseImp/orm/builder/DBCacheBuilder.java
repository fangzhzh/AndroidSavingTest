package com.example.zhenfang.myapplication.databaseImp.orm.builder;

import com.example.zhenfang.myapplication.databaseImp.orm.bean.DBCache;

/**
 * @author zhangzf
 * @since 6/22/15 5:34 PM
 */
public class DBCacheBuilder {
    private String key;
    private String value;

    private DBCacheBuilder() {
    }

    public static DBCacheBuilder aDBCache() {
        return new DBCacheBuilder();
    }

    public DBCacheBuilder withKey(String key) {
        this.key = key;
        return this;
    }

    public DBCacheBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public DBCacheBuilder but() {
        return aDBCache().withKey(key).withValue(value);
    }

    public DBCache build() {
        DBCache dBCache = new DBCache();
        dBCache.setKey(key);
        dBCache.setValue(value);
        return dBCache;
    }
}
