package com.hongyan.xcj.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.hongyan.xcj.core.BaseApplication;


/**
 * SoftInputUtils
 * 软键盘工具类
 *
 * @author ljnalex 整理 2015-12-25
 */
public class SoftInputUtils {

    /**
     * 隐藏软键盘
     *
     * @param context 软键盘所关联的窗口上下文
     * @param view    调起软键盘的View实例
     */
    public static void hideSoftKeyPad(Context context, View view) {
        try {
            if (view == null) {
                return;
            } else if (view.getWindowToken() == null) {
                return;
            }

            InputMethodManager inputManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Throwable ex) {
        }
    }

    /**
     * 显示软键盘
     *
     * @param context 软键盘所关联的窗口上下文
     * @param view    调起软键盘的View实例
     */
    public static void showSoftKeyPad(Context context, View view) {
        try {
            InputMethodManager inputManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN);
        } catch (Throwable ex) {
        }
    }

    /**
     * 延时显示软键盘
     *
     * @param context 软键盘所关联的窗口上下文
     * @param view    调起软键盘的View实例
     */
    public static void showSoftKeyPadDelayed(final Context context, final View view) {
        BaseApplication.getInstance().postTaskInUIThread(new Runnable() {

            @Override
            public void run() {
                showSoftKeyPad(context, view);
            }
        }, 400);
    }

    /**
     * 判断是不是这个view打开的软键盘
     *
     * @param context context
     * @param view    要检查的view
     * @return
     */
    public static boolean isActiveSoftKeyPad(Context context, View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            return inputMethodManager.isActive(view);
        } catch (Throwable ex) {
        }
        return false;
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param view
     * @param x
     * @param y
     * @return
     */
    public static boolean isShouldHideKeyboard(View view, double x, double y) {
        if (view != null && (view instanceof EditText)) {
            int[] tLocation = new int[2];
            view.getLocationInWindow(tLocation);
            int tLeft, tTop, tRight, tBottom;
            tLeft = tLocation[0];
            tRight = tLeft + view.getWidth();
            tTop = tLocation[1];
            tBottom = tTop + view.getHeight();
            if (x > tLeft && x < tRight && y > tTop && y < tBottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }
}
