package com.hongyan.xcj.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gson工具类
 *
 * @author abu created 2015-12-29
 */
public class GsonUtils {

    /**
     * 对象转换Json字符串
     *
     * @param obj 对象实例
     * @return json字符串
     */
    public static String toJson(Object obj) {
        long start = System.currentTimeMillis();
        try {
            Gson gson = new Gson();

            return gson.toJson(obj);
        } catch (Exception e) {
        }
        return null;
    }


    /**
     * 对象转换Json字符串处理有特殊字符的情况
     *
     * @param obj 对象实例
     * @return json字符串
     */
    public static String toJsonForSpecial(Object obj) {
        long start = System.currentTimeMillis();
        try {
            GsonBuilder gb = new GsonBuilder();
            gb.disableHtmlEscaping();
            String jsonStr = gb.create().toJson(obj);
            return jsonStr;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Json字符串转换成对象
     *
     * @param jsonString json字符串
     * @param className  对象类名称
     * @param <T>        类泛型
     * @return 根据泛型将json字符串转换成具体类
     */
    public static <T> T gsonResolve(String jsonString, Class<T> className) {
        if (jsonString == null) {
            return null;
        }

        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonString, className);
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * json字符串转换为集合
     *
     * @param jsonString json字符串
     * @param type       反射类型
     * @param className  类名称
     * @param <T>        泛型
     * @return 具体类的集合列表
     */
    public static <T> ArrayList<T> fromJsonToArrayList(String jsonString, Type type, Class<T> className) {
        if (jsonString == null || type == null) {
            return null;
        }

        long start = System.currentTimeMillis();

        try {
            Gson gson = new Gson();

            return gson.fromJson(jsonString, type);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * json字符串转换为集合
     *
     * @param jsonString json字符串
     * @param type       反射类型
     * @param className  类名称
     * @param <T>        泛型
     * @return 具体类的集合列表
     */
    public static <T> List<T> fromJsonToList(String jsonString, Type type, Class<T> className) {
        if (jsonString == null || type == null) {
            return null;
        }

        long start = System.currentTimeMillis();

        try {
            Gson gson = new Gson();

            return gson.fromJson(jsonString, type);
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * ArrayList转json数组
     *
     * @param list 集合列表
     * @return jsonArray字符串
     */
    public static String arrayListToJsonArray(ArrayList<String> list) {
        String result = "";

        if (list != null && list.size() > 0) {
            result += "[";
            for (int i = 0; i < list.size(); i++) {
                String f = list.get(i);
                result += String.format("%s", f);
                if (i != list.size() - 1) {
                    result += ",";
                }
            }
            result += "]";
        }

        return result;
    }

    /**
     * json 转 map
     *
     * @param jsonString
     * @param type
     * @return
     */
    public static HashMap<String, String> jsonToMap(String jsonString, Type type) {
        if (jsonString == null || jsonString.length() == 0) {
            return null;
        }
        try {
            Gson gson = new Gson();
            return gson.<HashMap>fromJson(jsonString, type);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 将Map转化为Json
     *
     * @param map
     * @return String
     */
    public static <T> String mapToJson(Map<String, T> map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    public static JsonElement mapToJsonElement(Map<String, String> map) {
        Gson gson = new Gson();
        return gson.toJsonTree(map);
    }

}
