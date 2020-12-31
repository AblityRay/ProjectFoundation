package com.haier.hailicommontlib.mvp.model.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.haier.hailicommontlib.mvp.model.bean.ParameterBeen;

import java.io.Serializable;

/**
 * @author： jjf
 * @date： 2020/9/11
 * @describe：控制页面跳转
 */
public class PageSwitchUtil {


    public static <T> void changeActivityPage(String path, ParameterBeen... objs) {
        changeActivityPage(null, path, objs);
    }

    public static <T> void changeActivityPage(Activity activity, String path, ParameterBeen... objs) {
        changeActivityPage(activity, path, null, objs);
    }

    public static <T> void changeActivityPage(Activity activity, String path, NavCallback navCallback, ParameterBeen... objs) {
        changeActivityPage(activity, path, -1, navCallback, objs);
    }

    /**
     * 要跳转的页面
     *
     * @param activity
     * @param path
     * @param requestCode 如果参数大于-1 则启动方式相当于{@link Activity#startActivityForResult(Intent, int)}
     * @param navCallback
     * @param objs
     */
    public static void changeActivityPage(Activity activity, String path, int requestCode, NavCallback navCallback, ParameterBeen... objs) {
        Postcard postcard = ARouter.getInstance().build(path);
        if (objs != null && objs.length > 0) {
            for (ParameterBeen parameterBeen : objs) {
                if (parameterBeen.getValue() instanceof Integer) {
                    postcard.withInt(parameterBeen.getKey(), (Integer) parameterBeen.getValue());
                } else if (parameterBeen.getValue() instanceof Short) {
                    postcard.withShort(parameterBeen.getKey(), (Short) parameterBeen.getValue());
                } else if (parameterBeen.getValue() instanceof Long) {
                    postcard.withLong(parameterBeen.getKey(), (Long) parameterBeen.getValue());
                } else if (parameterBeen.getValue() instanceof Byte) {
                    postcard.withByte(parameterBeen.getKey(), (Byte) parameterBeen.getValue());
                } else if (parameterBeen.getValue() instanceof Double) {
                    postcard.withDouble(parameterBeen.getKey(), (Double) parameterBeen.getValue());
                } else if (parameterBeen.getValue() instanceof Float) {
                    postcard.withFloat(parameterBeen.getKey(), (Float) parameterBeen.getValue());
                } else if (parameterBeen.getValue() instanceof String) {
                    postcard.withString(parameterBeen.getKey(), (String) parameterBeen.getValue());
                } else if (parameterBeen.getValue() instanceof Character) {
                    postcard.withChar(parameterBeen.getKey(), (Character) parameterBeen.getValue());
                } else if (parameterBeen.getValue() instanceof Boolean) {
                    postcard.withBoolean(parameterBeen.getKey(), (Boolean) parameterBeen.getValue());
                } else if (parameterBeen.getValue() instanceof Bundle) {
                    postcard.withBundle(parameterBeen.getKey(), (Bundle) parameterBeen.getValue());
                } else if (parameterBeen.getValue() instanceof Serializable) {
                    postcard.withSerializable(parameterBeen.getKey(), (Serializable) parameterBeen.getValue());
                } else if (parameterBeen.getValue() instanceof Parcelable) {
                    postcard.withParcelable(parameterBeen.getKey(), (Parcelable) parameterBeen.getValue());
                }
            }
        }
        if (activity != null) {

                if (requestCode > -1) {
                    if (navCallback != null) {
                        postcard.navigation(activity, requestCode, navCallback);
                    } else {
                        postcard.navigation(activity, requestCode);
                    }
                } else {
                    if (navCallback != null) {
                        postcard.navigation(activity, navCallback);
                    } else {
                        postcard.navigation(activity);
                    }
                }

        } else {
            postcard.navigation();
        }

    }
}
