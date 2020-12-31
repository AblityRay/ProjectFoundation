package com.haier.hailicommontlib.mvp.model.interfaces;


import android.app.Application;
import android.content.res.Configuration;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author： jjf
 * @date： 2020/9/16
 * @describe：功能module的假性Application
 */

public interface IBaseApplication extends IProvider {
    public String TAG = "IBaseApplication";

    public void onCreate(Application application);

    public void onTerminate();

    public void onLowMemory();

    public void onTrimMemory(int level);

    public void onConfigurationChanged(Configuration newConfig);
}
