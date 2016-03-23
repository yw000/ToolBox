package com.yang.toolbox.util;

/**
 * Toast统一管理类
 */

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.yang.toolbox.app.AppConfig;

public class ToastHelper {
    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    private ToastHelper() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        showToast(context, message, Toast.LENGTH_SHORT);
    }

    public static void _debugShort(Context context, CharSequence message) {
        if (AppConfig.IS_DEBUG)
            showToast(context, message, Toast.LENGTH_SHORT);
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        showToast(context, message, Toast.LENGTH_SHORT);

    }

    public static void _debugShort(Context context, int message) {
        if (AppConfig.IS_DEBUG)
            showToast(context, message, Toast.LENGTH_SHORT);
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }

    public static void _debugLong(Context context, CharSequence message) {
        if (AppConfig.IS_DEBUG)
            showToast(context, message, Toast.LENGTH_LONG);
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }

    public static void _debugLong(Context context, int message) {
        if (AppConfig.IS_DEBUG)
            showToast(context, message, Toast.LENGTH_LONG);
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        Toast.makeText(context, message, duration).show();
    }


    private static void showToast(Context mContext, CharSequence text, int duration) {

        mHandler.removeCallbacks(r);
        if (mToast != null)
            mToast.setText(text);
        else
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
//        mHandler.postDelayed(r, duration);
        mToast.show();
    }

    private static void showToast(Context mContext, int text, int duration) {

        mHandler.removeCallbacks(r);
        if (mToast != null)
            mToast.setText(text);
        else
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
//        mHandler.postDelayed(r, duration);
        mToast.show();
    }
}