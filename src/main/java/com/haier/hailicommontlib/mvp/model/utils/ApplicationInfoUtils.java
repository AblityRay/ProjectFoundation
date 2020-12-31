package com.haier.hailicommontlib.mvp.model.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.haier.hailicommontlib.BaseApplication;

/**
 * @author： jjf
 * @date： 2020/9/24
 * @describe：
 */
public class ApplicationInfoUtils {

    public static String getString(String valueName) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = BaseApplication.getInstance().getApplicationContext()
                    .getPackageManager().getApplicationInfo(BaseApplication.getInstance().getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null && appInfo.metaData != null) {
                return appInfo.metaData.getString(valueName);
            } else {
                LogUtil.I("ApplicationInfo", "需要在AndroidManifest.xml中配置" + valueName + " meta数据");
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static Integer getInteger(String valueName) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = BaseApplication.getInstance().getApplicationContext()
                    .getPackageManager().getApplicationInfo(BaseApplication.getInstance().getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null && appInfo.metaData != null) {
                return appInfo.metaData.getInt(valueName);
            } else {
                LogUtil.I("ApplicationInfo", "需要在AndroidManifest.xml中配置" + valueName + " meta数据");
                return 0;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
