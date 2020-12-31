package com.haier.hailicommontlib;

import android.content.pm.PackageManager;

/**
 * @author： jjf
 * @date： 2020/9/11
 * @describe：
 */
public class ApplicationUtils {
    public static BaseApplication getAppContext() {
        return BaseApplication.getInstance();
    }


    public static String appUpdate() {
        String appVersion = "-1";
        try {
            appVersion = getAppContext().getPackageManager().getPackageInfo(ApplicationUtils.getAppContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            appVersion = "-1";
            e.printStackTrace();
        }
        return appVersion;
    }

}
