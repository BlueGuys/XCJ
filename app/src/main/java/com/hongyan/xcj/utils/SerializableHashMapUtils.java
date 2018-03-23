package com.hongyan.xcj.utils;

import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;

public class SerializableHashMapUtils implements Serializable {

    private static final long serialVersionUID = 8213420209495595034L;

    private HashMap<String, String> mMap = new HashMap<String, String>();

    /**
     * 向mMap放入键值对
     *
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        if (value == null) {
            Log.e("test", "参数异常" + key + "=null");
            return;
        }
        mMap.put(key, value);
    }

    /**
     * 将map放入mMap
     *
     * @param map
     */
    public void putAll(HashMap<String, String> map) {
        mMap.putAll(map);
    }

    /**
     * 根据key得到value
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return this.mMap.get(key);
    }

    /**
     * 得到mMap
     *
     * @return
     */
    public HashMap<String, String> getMap() {
        return this.mMap;
    }

}
