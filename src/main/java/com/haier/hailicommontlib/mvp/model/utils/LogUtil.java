package com.haier.hailicommontlib.mvp.model.utils;

import android.util.Log;

import com.haier.hailicommontlib.ApplicationUtils;

/**
 * Log 工具类
 */
public class LogUtil {

    public static String TAG = "HaierWasherTopSpeed";

    public static void V(String tag, String msg) {
        if (ApplicationUtils.getAppContext().APP_LOG) {
            Log.v(tag, msg);
        }
    }

    public static void E(String tag, String msg) {
        if (ApplicationUtils.getAppContext().APP_LOG) {
            Log.e(tag, msg);
        }

    }

    public static void D(String tag, String msg) {
        if (ApplicationUtils.getAppContext().APP_LOG) {
            Log.d(tag, msg);
        }
    }

    public static void I(String tag, String msg) {
        if (ApplicationUtils.getAppContext().APP_LOG) {
            Log.i(tag, msg);
        }
    }

    public static void W(String tag, String msg) {
        if (ApplicationUtils.getAppContext().APP_LOG) {
            Log.w(tag, msg);
        }
    }
}
