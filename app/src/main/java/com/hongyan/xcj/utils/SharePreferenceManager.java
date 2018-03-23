package com.hongyan.xcj.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.hongyan.xcj.core.BaseApplication;

public class SharePreferenceManager {

    private static volatile SharePreferenceManager instance;
    private Context mContext;
    private SharedPreferences sharedPreferences;


    private SharePreferenceManager() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        if (context == null) {
            return;
        }
        sharedPreferences = context.getSharedPreferences("xcj", Context.MODE_PRIVATE);
    }

    public static SharePreferenceManager getInstance() {
        if (instance == null) {
            synchronized (SharePreferenceManager.class) {
                if (instance == null) {
                    instance = new SharePreferenceManager();
                }
            }
        }
        return instance;
    }

    public void putString(String key, String value) {
        if (sharedPreferences == null) {
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        if (sharedPreferences == null) {
            return null;
        }
        return sharedPreferences.getString(key, "");
    }

    public boolean isExist(String key) {
        if (sharedPreferences == null) {
            return false;
        }
        String str = sharedPreferences.getString(key, "");
        return !StringUtils.isEmpty(str);
    }

    public void deleteStr(String key) {
        if (sharedPreferences == null) {
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }


}
