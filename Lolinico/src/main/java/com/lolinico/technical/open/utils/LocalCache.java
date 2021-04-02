package com.lolinico.technical.open.utils;

import android.app.Activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Rico on 2017/8/3.
 */
public class LocalCache {
    public static List saveList;
    public static HashMap<String, Object> saveMap;
    public static LocalCache mLocalCache;
    public static LinkedHashMap<String, Activity> activityHashMap;

    /**
     * 单例
     *
     * @return
     */
    public static LocalCache getInstance() {
        if (mLocalCache == null) {
            saveMap = new HashMap<>();
            saveList = new ArrayList();
            mLocalCache = new LocalCache();
            activityHashMap = new LinkedHashMap<>();
        }
        return mLocalCache;
    }

    /**
     * save object to list
     *
     * @param object
     */
    public void putListValue(Object object) {
        if (saveList != null) {
            saveList.add(object);
        }
    }

    /**
     * get list
     *
     * @return
     */
    public List getSaveList() {
        if (saveList != null) {
            return saveList;
        }
        return new ArrayList();
    }

    /**
     * save object to pool
     *
     * @param key
     * @param object
     */
    public void putPoolValue(String key, Object object) {
        if (saveMap != null) {
            saveMap.put(key, object);
        }
    }

    /**
     * get object from pool
     *
     * @param key
     * @return
     */
    public Object getPoolValue(String key) {
        if (saveMap != null) {
            return saveMap.get(key);
        }
        return null;
    }

    public void putPoolActivity(String key, Activity activity) {
        if (activityHashMap != null) {
            activityHashMap.put(key, activity);
        }
    }

    public HashMap getPoolActivity() {
        return activityHashMap;
    }

    public void removePoolActivity(String key) {
        activityHashMap.remove(key);
    }

    public static void realseCache(){
        if (saveList != null) {
            saveList.clear();
        }
        if (saveMap != null) {
            saveMap.clear();
        }
        if (activityHashMap != null) {
            activityHashMap.clear();
        }
    }
}
