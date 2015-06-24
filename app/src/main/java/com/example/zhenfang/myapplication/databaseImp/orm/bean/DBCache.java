package com.example.zhenfang.myapplication.databaseImp.orm.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author zhangzf
 * @since 6/22/15 4:54 PM
 */
@DatabaseTable(tableName = "cache_info")
public class DBCache {
    public interface COLUMN {
        String ID = "id";
        String KEY = "key";
        String VALUE = "value";
    }

    @DatabaseField(generatedId = true, columnName = COLUMN.ID)
    private int id;

    @DatabaseField(index = true, columnName = COLUMN.KEY)
    private String key;

    @DatabaseField(columnName = COLUMN.VALUE)
    private String value;

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
