package com.example.zhenfang.myapplication.database;

/**
 * @author amulya
 * @datetime 04 Apr 2014, 3:06 PM
 */
public abstract class UpgradeScript {

    private int minVersion;
    private int scriptVersion;
    /**
     * @param minVersion    the minimum version that is required for this script to run. (if version
     *                      is older than minimum version, do not run this script)
     * @param scriptVersion this version when this script is introduced. (run this script if new
     *                      version is at least version of the script)
     */
    protected UpgradeScript(int minVersion, int scriptVersion) {
        this.minVersion = minVersion;
        this.scriptVersion = scriptVersion;
    }

    /**
     * Scripts runs if the @oldVersion >= @minVersion (old com.example.zhenfang.myapplication.database version is at least min supported script version)
     * and, @oldVersion < @scriptVersion (old com.example.zhenfang.myapplication.database does not have the script changes)
     * and, @newVersion >= scriptVersion (final version of update requires this script)
     *
     * @param oldVersion old com.example.zhenfang.myapplication.database version
     * @param newVersion new version to which com.example.zhenfang.myapplication.database is being updated
     * @return true, if script is eligible to run
     */
    public boolean shouldRunScript(int oldVersion, int newVersion) {
        return (oldVersion >= minVersion && oldVersion < scriptVersion && newVersion >= scriptVersion);
    }

    public abstract String getScriptSQL();
}
