package com.haier.hailicommontlib.mvp_api.view;

import android.view.View;

import com.haier.hailicommontlib.mvp_api.presenter.IPresenter;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import me.jessyan.autosize.internal.CustomAdapt;
//import me.jessyan.autosize.internal.CustomAdapt;


/**
 * @author：焦俊峰
 * @date：2017/12/19
 * @describe：
 */
// 如果不想支持，则实现CancelAdapt
public interface Iview extends    CustomAdapt {

    /**
     * 返回当前视图需要的layout的id
     *
     * @return
     */
    @LayoutRes
    int getLayoutId();

    /**
     * 根据id获取view
     *
     * @param id
     * @param <V>
     * @return
     */
    <V extends View> V findViewById(@IdRes int id);


    /**
     * 绑定Presenter
     */
    IPresenter bindPresenter();


    /**
     * 初始化控件
     */
    void initView();

    /**
     * 添加点击事件以及用户交互逻辑
     */
    void initData();

//    /**
//     * 显示loading
//     */
//    void showLoading();
//    /**
//     * 隐藏loading
//     */
//    void highLoading();

    /**
     * 点击无网络刷新界面
     */
    void refreshPage();
}
