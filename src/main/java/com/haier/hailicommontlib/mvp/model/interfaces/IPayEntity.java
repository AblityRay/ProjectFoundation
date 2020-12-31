package com.haier.hailicommontlib.mvp.model.interfaces;

import android.content.Context;

/**
 * @author： jjf
 * @date： 2020/9/24
 * @describe：支付实体类
 */
public interface IPayEntity  {

    /**
     * 支付方法
     *
     * @param context      上下文
     * @param iPayParament 支付参数
     */
     void pay(Context context, IPayParament iPayParament, PayResultListener<IPayResultBean> payResultListener);


    /**
     * 资源销毁
     */
    void onDestroy();
}
