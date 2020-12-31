package com.haier.hailicommontlib.mvp.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.haier.hailicommontlib.BaseApplication;
import com.haier.hailicommontlib.R;
import com.haier.hailicommontlib.mvp.model.utils.ApplicationInfoUtils;
import com.haier.hailicommontlib.mvp.model.utils.LogUtil;
import com.haier.hailicommontlib.mvp.model.utils.Rom;
import com.haier.hailicommontlib.mvp.model.utils.WindowsUtil;
import com.haier.hailicommontlib.mvp.model.utils.loading.LoadUtil;
import com.haier.hailicommontlib.mvp.view.custom.FontTextView;
import com.haier.hailicommontlib.mvp_api.presenter.IPresenter;
import com.haier.hailicommontlib.mvp_api.view.Iview;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import me.jessyan.autosize.AutoSizeCompat;
import me.jessyan.autosize.internal.CancelAdapt;

import static com.haier.hailicommontlib.BaseApplication.SystemExit;


/**
 * jjf
 * Activity 基础类
 */

public abstract class BaseActivity extends FragmentActivity implements Iview, View.OnClickListener, CancelAdapt {
    public IPresenter iPresenter;
    public  String TAG = "BaseActivity";
    private int layoutId;
    private View view;
    public Toolbar toolbar;
    View notNetWork;
    View notData;
    public LinearLayout toolbarBottomView;
    public Bundle savedInstanceState;
    public PersistableBundle persistentState;
//    @Override
//    public final void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//        this.   savedInstanceState=savedInstanceState;
//        this.   persistentState=persistentState;
//    }

    private View parentView;
    private TextView notDataMessage;
    private LoadUtil loadUtil;

    /**
     *  等待Loading
     * @param context
     * @param type
     * @return
     */
    public LoadUtil getLoadUtil(Context context,int type) {
        if(loadUtil==null){
            loadUtil=new LoadUtil(context,type);
        }
        return loadUtil;
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        //竖屏ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        iPresenter = bindPresenter();
        if (iPresenter != null) {
             iPresenter.setView(this);
        }
        LogUtil.I("BaseActivity", "" + getClass().getSimpleName());
        //获取本view层对应的P层

        layoutId = getLayoutId();
        view = View.inflate(this, layoutId, null);
        parentView = View.inflate(this, R.layout.activity_base, null);
        toolbar = parentView.findViewById(R.id.include2);
        toolbarBottomView = toolbar.findViewById(R.id.toolbar_bottom);

        //添加无数据以及无网络UI
        notNetWork = parentView.findViewById(R.id.utiles_reload_the);
        notData = parentView.findViewById(R.id.utiles_temporarily_no_data);
        notNetWork.findViewById(R.id.reload_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPage();
            }
        });

        //添加子类布局
        FrameLayout frameLayout = parentView.findViewById(R.id.fly_);
        frameLayout.addView(view);

        notDataMessage = parentView.findViewById(R.id.tv_notdata_message);

        addDefultView(notData, notNetWork);

        setContentView(parentView);

        initView();
        initData();
        BaseApplication.addActivity(this);


        Log.i(TAG, getPackageName() + "--" + getLocalClassName());

    }


    /**
     * 如果参数为null，则隐藏控件 如果不想改变默认值则传""
     *
     * @param content
     */
    public void notDataPageMessageShow(String content) {

        if (content == null) {
            notDataMessage.setVisibility(View.GONE);
        } else {
            notDataMessage.setVisibility(View.VISIBLE);
            if (content.length() > 0) {
                notDataMessage.setText(content);
            }
        }

    }


    /**
     * 查询控件，直接设置监听事件
     *
     * @param id
     * @param <V>
     * @return
     */
    public <V extends View> V findViewById(int id) {
        return findViewById(id, false);
    }

    /**
     * 查询控件，直接设置监听事件
     *
     * @param id
     * @param setOnClickListener
     * @param <V>
     * @return
     */
    public <V extends View> V findViewById(int id, boolean setOnClickListener) {
        V v = view.findViewById(id);
        if (setOnClickListener) {
            v.setOnClickListener(this);
        }
        return v;
    }


    /**
     * @param title
     * @param id    1是有返回键的 0 是没有返回键
     * @param view
     */
    public void setTitle(String title, int id, View... view) {
        if (toolbar != null) {
            RelativeLayout toolbarTop = toolbar.findViewById(R.id.lly_toolbar_top);
            toolbarTop.setVisibility(View.VISIBLE);
            toolbar.setNavigationIcon(null);
            FontTextView tvTitle = toolbar.findViewById(R.id.toolbar_title);
            ImageView backImage = toolbar.findViewById(R.id.iv_back_image);
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            if (id == 1) {
                backImage.setVisibility(View.VISIBLE);
                backImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBack();
                        onBackPressed();


                    }
                });
            } else {
                backImage.setVisibility(View.GONE);
            }

            tvTitle.setText("" + title);
            tvTitle.setTextColor(this.getResources().getColor(R.color.toobar_text));

            if (view != null && view.length > 0) {
                LinearLayout linearLayout = toolbar.findViewById(R.id.lly_right_view);
                for (int i = 0; i < view.length; i++) {
                    if (view[i] != null) {
                        linearLayout.addView(view[i]);
                    }
                }
            }

        }
    }

    /**
     * @param title
     * @param isHaveTop 顶部的返回键以及标题、右边的View是否显示
     */
    public void setTitle(String title, boolean isHaveTop) {
        if (toolbar != null) {
            RelativeLayout linearLayout = toolbar.findViewById(R.id.lly_toolbar_top);
            if (isHaveTop) {
                linearLayout.setVisibility(View.VISIBLE);
            } else {
                linearLayout.setVisibility(View.GONE);
            }

        }
    }

    /**
     * @param title
     * @param id                0 是没有返回键  1是有返回键的, 返回键图标为默认的白色  2是返回键为灰色
     * @param view
     * @param backgroundColorId
     * @param titleTextColorId
     */

    @SuppressLint("ResourceType")
    protected void setTitle(String title, int id, @ColorRes int backgroundColorId, @ColorRes int titleTextColorId, View... view) {
        if (toolbar != null) {
            TextView tvTitle = toolbar.findViewById(R.id.toolbar_title);
            RelativeLayout toolbarTopView = toolbar.findViewById(R.id.lly_toolbar_top);
            toolbarTopView.setVisibility(View.VISIBLE);
            toolbar.setTitle(" ");
            tvTitle.setText("" + title);
            ImageView backImage = toolbar.findViewById(R.id.iv_back_image);
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            switch (id) {
                case 1:
                    backImage.setVisibility(View.VISIBLE);
                    backImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBack();
                            onBackPressed();
                        }
                    });
                    break;
                case 2:
                    //箭头为灰色，头部背景很大可能为白色，状态栏字体需设置为黑色-------开发之初庆伟没做，这个坑暂时这么填，以后需要更新架构时，在统一设计
                    setStatusBar();
                    backImage.setImageDrawable(getResources().getDrawable(R.drawable.icon_fanhui_gray));
                    backImage.setVisibility(View.VISIBLE);
                    backImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBack();
                            onBackPressed();
                        }
                    });
                    break;
                default:
                    backImage.setVisibility(View.GONE);
            }

            if (view != null && view.length > 0) {
                LinearLayout linearLayout = toolbar.findViewById(R.id.lly_right_view);
                for (int i = 0; i < view.length; i++) {
                    if (view[i] != null) {
                        linearLayout.addView(view[i]);
                    }
                }
            }
            if (backgroundColorId > 0) {
                toolbar.setBackgroundColor(this.getResources().getColor(backgroundColorId));
            }
            if (titleTextColorId > 0) {
                tvTitle.setTextColor(this.getResources().getColor(titleTextColorId));
            }
        }
    }

    /**
     * 返回键操作
     */
    public void onBack() {
    }


    /**
     * 是否显示头部
     *
     * @param isShow false 隐藏  true 显示
     */
    public void isShowToolbar(boolean isShow) {
        if (toolbar != null) {
            if (isShow) {
                toolbar.setVisibility(View.VISIBLE);
            } else {
                toolbar.setVisibility(View.GONE);
            }
        }
    }


    TextView tvUpdate;

    /**
     * toolbar 右上角添加View
     *
     * @param title
     * @param onClickListener
     */

    public View addTitleRightTextView(String title, View.OnClickListener onClickListener) {
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        tvUpdate = new TextView(this);
        tvUpdate.setTextColor(getResources().getColor(R.color.app_whit));
        tvUpdate.setTextSize(12);

        tvUpdate.setText(title);
        tvUpdate.setOnClickListener(onClickListener);

        params.setMargins(0, 0, getResources().getDimensionPixelSize(R.dimen.defult_padding_16), 0);
        tvUpdate.setLayoutParams(params);
        return tvUpdate;
    }

    /**
     * 倒计时用到（更改右上角文本被容）
     */
    public void setToolbarRightText(String str) {
        if (tvUpdate != null) {
            tvUpdate.setText(str);
        }
    }


    /**
     * toolbar 右上角添加View
     *
     * @param title
     * @param onClickListener
     * @param righhtTitleTextColorId 字体颜色
     */

    public View addTitleRightTextView(String title, @ColorRes int righhtTitleTextColorId, View.OnClickListener onClickListener) {
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        tvUpdate = new TextView(this);
        tvUpdate.setTextColor(getResources().getColor(righhtTitleTextColorId));
        tvUpdate.setTextSize(12);

        tvUpdate.setText(title);
        tvUpdate.setOnClickListener(onClickListener);

        params.setMargins(0, 0, getResources().getDimensionPixelSize(R.dimen.defult_padding_16), 0);
        tvUpdate.setLayoutParams(params);
        return tvUpdate;
    }

    /**
     * toolbar 右上角添加View
     *
     * @param id
     * @param onClickListener
     */
    public View addTitleRightImageView(@DrawableRes int id, View.OnClickListener onClickListener) {
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ImageView tvUpdate = new ImageView(this);
        tvUpdate.setImageDrawable(getResources().getDrawable(id));
        tvUpdate.setOnClickListener(onClickListener);

        params.setMargins(0, 0, getResources().getDimensionPixelSize(R.dimen.defult_padding_16), 0);
        tvUpdate.setLayoutParams(params);
        return tvUpdate;
    }

    /**
     * 在toolbar底部添加View
     *
     * @param view  传入为空，则隐藏
     */
    private int isAddTag = 0;

    public void addViewInToolbarBottom(View view) {
        if (view == null) {
            toolbarBottomView.setVisibility(View.GONE);
        } else {
            toolbarBottomView.setVisibility(View.VISIBLE);
            if (isAddTag == 0) {
                toolbarBottomView.addView(view);
            }
            isAddTag = 1;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.removeActivity(this);
        LogUtil.I(TAG, "onDestroy=" + this.toString());
        if (BaseApplication.baseActivities != null && BaseApplication.baseActivities.size() == 0) {
            SystemExit();
        }
    }

    //与本UI层对应的一个P层
    public abstract IPresenter bindPresenter();

    /**
     * 返回当前视图需要的layout的id
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 添加点击事件以及用户交互逻辑
     */
    public abstract void initData();


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();


    }


    /**
     * flyme状态栏字体颜色
     *
     * @param dark
     * @return
     */
    public static boolean setMeizuStatusBarDarkIcon(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

    ArrayList<View> views = new ArrayList<>();

    /**
     * 添加无网络以及无数据View
     */
    public void addDefultView(View... view) {
        views.clear();
        View[] view1 = view;
        for (int i = 0; i < view1.length; i++) {
            if (view1[i].getId() == R.id.utiles_temporarily_no_data) {
                NOT_DATA = i;
            } else if (view1[i].getId() == R.id.utiles_reload_the) {
                NOT_NETWORK = i;
            }
            views.add(view1[i]);
        }
    }

    public int NOT_DATA = 0; //没有数据
    public int NOT_NETWORK = 1;//没有网络
    public int INIT_VIEW = 2;//初始化值

    /**
     * 根据页面状态选择View 的显示
     * 输入  -1   全部隐藏
     *
     * @param position
     */
    public void dataStute(View xlvBill, int position) {
        if (views.size() > position) {
            for (int i = 0; i < views.size(); i++) {
                if (xlvBill != null) {
                    if (position == NOT_NETWORK) {
                        xlvBill.setVisibility(View.GONE);
                    } else if (!xlvBill.isShown()) {
                        xlvBill.setVisibility(View.VISIBLE);
                    }
                }
                if (i == position) {
                    views.get(i).setVisibility(View.VISIBLE);
                } else {
                    views.get(i).setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                WindowsUtil.hideKeyboard(ev, view, BaseActivity.this);//调用方法判断是否需要隐藏键盘
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public final void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MIUISetStatusBarLightMode(getWindow(), true);
        } else {
            setStaturBarOf6_0(R.color.app_black_6);
        }

    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上(小米系统)
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            LogUtil.I(TAG, "Rom.isMiui()=" + Rom.isMiui() + "  Rom.isEmui()=" + Rom.isEmui());
            if (Rom.isMiui()) {
                Class clazz = window.getClass();
                try {
                    int darkModeFlag = 0;
                    Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                    Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                    darkModeFlag = field.getInt(layoutParams);
                    Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                    if (dark) {
                        extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                    } else {
                        extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                    }
                    result = true;
                } catch (Exception e) {

                }
            } else if (Rom.isFlyme()) {
                FlymeSetStatusBarLightMode(window, dark);
            } else {
                setStaturBarOf6_0();
            }
        }

        return result;
    }

    /**
     * 设置魅族手机状态栏图标颜色风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void setStaturBarOf6_0() {

        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            //清楚所有状态
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            View decorView = window.getDecorView();

            //设置状态栏文字颜色及图标为深色
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
            //设置标记
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //状态栏背景透明
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            window.setAttributes(localLayoutParams);
        }
        //设置状态栏文字颜色及图标为浅色
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    public void setStaturBarOf6_0(@ColorRes int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(colorResId));

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBackPressed();
            onBack();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 是否按照宽度进行等比例适配 (为了保证在高宽比不同的屏幕上也能正常适配, 所以只能在宽度和高度之中选择一个作为基准进行适配)
     *
     * @return {@code true} 为按照宽度进行适配, {@code false} 为按照高度进行适配
     */
    @Override
    public boolean isBaseOnWidth() {
        return true;
    }

    /**
     * 设计图尺寸为 1080px * 1920px, 高换算成 dp 为 960 (1920px / 2 = 960dp)
     * <p>
     * 返回的设计尺寸, 单位 dp
     * {@link #getSizeInDp} 须配合 {@link #isBaseOnWidth()} 使用, 规则如下:
     * 如果 {@link #isBaseOnWidth()} 返回 {@code true}, {@link #getSizeInDp} 则应该返回设计图的总宽度
     * 如果 {@link #isBaseOnWidth()} 返回 {@code false}, {@link #getSizeInDp} 则应该返回设计图的总高度
     * 如果您不需要自定义设计图上的设计尺寸, 想继续使用在 AndroidManifest 中填写的设计图尺寸, {@link #getSizeInDp} 则返回 {@code 0}
     *
     * @return 单位 dp
     */
    @Override
    public float getSizeInDp() {
        float sizeInDP = 0;

        sizeInDP = ApplicationInfoUtils.getInteger(isBaseOnWidth() ? "design_width_in_dp" : "design_height_in_dp");
        return sizeInDP;//375   667
    }

    @Override
    public Resources getResources() {
        float sizeInDP = getSizeInDp();
        if (getSizeInDp() == 0) {
            sizeInDP = ApplicationInfoUtils.getInteger(isBaseOnWidth() ? "design_width_in_dp" : "design_height_in_dp" );
        }
        //需要升级到 v1.1.2 及以上版本才能使用 AutoSizeCompat
//        AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources());//如果没有自定义需求用这个方法
        AutoSizeCompat.autoConvertDensity(super.getResources(), sizeInDP, isBaseOnWidth());//如果有自定义需求就用这个方法
        return super.getResources();
    }
}
