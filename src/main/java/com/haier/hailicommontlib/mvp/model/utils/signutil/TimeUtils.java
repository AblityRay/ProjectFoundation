package com.haier.hailicommontlib.mvp.model.utils.signutil;

import android.content.pm.PackageManager;

import com.haier.hailicommontlib.ApplicationUtils;

import java.util.TimeZone;

/**
 * @author：宋庆伟
 * @date：2020/9/17
 * @describe：加密方式时间设置与版本
 */
public class TimeUtils {

    /**
     * 获取时区 * @return
     */
    public static String getCurrentTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        String strTz = tz.getDisplayName(false, TimeZone.SHORT);
        return strTz;
    }



}
