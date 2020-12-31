package com.haier.hailicommontlib;

import android.content.Context;
import android.graphics.Bitmap;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haier.hailicommontlib.mvp.model.baidulocation.LocationService;
import com.haier.hailicommontlib.mvp.model.pushreceiver.TheirAlliesPush;
import com.haier.hailicommontlib.mvp.model.utils.LogUtil;
import com.haier.hailicommontlib.mvp.model.utils.ToastUtil;
import com.haier.hailicommontlib.mvp.model.volley.volley_network.HttpManager;
import com.haier.hailicommontlib.mvp.view.activity.BaseActivity;

import java.util.ArrayList;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

/**
 * @author： jjf
 * @date： 2020/9/11
 * @describe：
 */
public class BaseApplication extends MultiDexApplication {
    public String TAG = "BaseApplication";

    public static ArrayList<BaseActivity> baseActivities;

    /**
     * 网络参数
     */
    private static Bitmap.CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
    private static int DISK_IMAGECACHE_QUALITY = 100;

    private static BaseApplication instance;
    /**
     * App开发环境
     */
    public boolean APP_IS_ONLINE = false;
    /**
     * log开关
     */
    public boolean APP_LOG = true;
    /**
     * 友盟正式测试
     */
    public static boolean UMENG_CHEnnID = false;
    /**
     * 定位
     */
    public LocationService locationService;

    /**
     * 友盟
     */
    public TheirAlliesPush theirAlliesPush;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            instance = this;
            LogUtil.I(TAG, "BaseApplication--");
            //初始化网络框架
            initHttpManager();
            //路由加载
            initRouter();
        } catch (Exception e) {
//            ToastUtil.showShortToast(this, "BaseApplication=" + e.toString());
        }
        //定位初始化
//        initLoation();
        //友盟初始化
//        initUmengSDK();
    }

    // //定位初始化
    private void initLoation() {
        locationService = new LocationService(this);
    }

    //友盟初始化
    public void initUmengSDK() {
        //判读用户是否同意隐私政策如果同意就直接初始化友盟否则就预初始化友盟
//        UMConfigure.preInit(this, AppVariableChanges.UMENG_APPKEY, "Haili");//友盟预初始化

        theirAlliesPush = new TheirAlliesPush(this);
    }

    public static BaseApplication getInstance() {
        return instance;
    }


    //路由加载
    private void initRouter() {
        if (!APP_IS_ONLINE) {       // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            //一定要在ARouter.init之前调用openDebug
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this); // As early as possible, it is recommended to initialize in the Application
//        GeneratedClass generatedClass
    }

    /**
     * 初始化网络工具
     */
    public void initHttpManager() {
        HttpManager.getInstance().init(this, this.getPackageCodePath(),
                DISK_IMAGECACHE_COMPRESS_FORMAT, DISK_IMAGECACHE_QUALITY,
                HttpManager.ImageCacheType.DISK);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 终止应用程序时调用，不能保证一定会被调用。
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        HttpManager.getInstance().cancelPendingRequests();//停止所有请求连接
        ARouter.getInstance().destroy();
        locationService.unregisterListener();//注销定位
        locationService.stop();//停止定位
    }

    /**
     * 当后台应用程序终止，但前台用用程序内存还不够时调用该方法，可在该方法中释放一些不必要的资源来应对这种情况。
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     * 知应用的不同内存情况
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }


    //创建活动
    public static void addActivity(BaseActivity baseActivity) {
        if (baseActivities == null) {
            baseActivities = new ArrayList<>();
        }
        baseActivities.add(baseActivity);
    }

    //销毁活动
    public static void removeActivity(BaseActivity baseActivity) {
        if (baseActivities == null) {
            baseActivities = new ArrayList<>();
        }
        baseActivities.remove(baseActivity);
    }

    /**
     * 一键退出应用
     */
    public static void SystemExit() {

        if (baseActivities != null) {
            for (int i = 0; i < baseActivities.size(); i++) {
                baseActivities.get(i).finish();
            }
        }
        baseActivities.clear();
        baseActivities = null;
        System.exit(0);
    }
}
