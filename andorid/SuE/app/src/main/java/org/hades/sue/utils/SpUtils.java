package org.hades.sue.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Hades on 17/7/31.
 *
 * SharePreference的工具类。
 *
 * 需要在Application的子类中调用init<>方法。
 */

public class SpUtils {


    private static SpUtils           spUtils = null;

    private static SharedPreferences sp;

    private SpUtils(){}

    /**
     * 初始化方法
     * @param ctx 上下文
     * @return 实例对象
     */
    public static SpUtils init(Context ctx) {
        if (spUtils == null) {
            spUtils = new SpUtils();
            sp = ctx.getSharedPreferences(Values.SNAME, Context.MODE_PRIVATE);
        }

        return spUtils;
    }


    /**
     * 存入Boolean变量
     * @param key    键
     * @param value  值
     */
    public void setBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 获取一个Boolean值
     * @param key
     * @param defValue
     * @return
     */
    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    /**
     * setInt
     * @param key
     * @param value
     */
    public void setInt(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

    /**
     * get a int value
     * @param key
     * @param defValue
     * @return
     */
    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    /**
     * put a string
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    /**
     * get a String value
     * @param key
     * @param defValue
     * @return
     */
    public String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    /**
     * remove a node by key;
     * @param key
     */
    public void remove(String key) {
        sp.edit().remove(key).commit();
    }
}
