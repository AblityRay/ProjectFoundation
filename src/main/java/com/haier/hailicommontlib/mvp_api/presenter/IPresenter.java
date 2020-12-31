package com.haier.hailicommontlib.mvp_api.presenter;


import com.haier.hailicommontlib.mvp_api.view.Iview;

/**
 * @author：焦俊峰
 * @date：2017/12/15
 * @describe：控制层统一接口
 */

public interface IPresenter {
    String TAG = "IPresenter";

    /**
     * 将View  与 Presenter 连接
     *
     * @param view
     */
    <T extends Iview> void setView(T view);

}
