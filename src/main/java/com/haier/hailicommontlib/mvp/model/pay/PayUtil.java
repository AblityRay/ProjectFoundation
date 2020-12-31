package com.haier.hailicommontlib.mvp.model.pay;

import android.app.Activity;
import android.widget.Toast;

import com.haier.hailicommontlib.mvp.model.bean.pay.WxPayInfoBeen;
import com.haier.hailicommontlib.mvp.model.enums.MethodOfPaymentEnum;
import com.haier.hailicommontlib.mvp.model.interfaces.IPayEntity;
import com.haier.hailicommontlib.mvp.model.interfaces.IPayParament;
import com.haier.hailicommontlib.mvp.model.interfaces.IPayResultBean;
import com.haier.hailicommontlib.mvp.model.interfaces.PayResultListener;
import com.haier.hailicommontlib.mvp.model.pay.entity.WxPay;
import com.haier.hailicommontlib.mvp.model.utils.ToastUtil;

import java.lang.ref.SoftReference;

/**
 * @author： jjf
 * @date： 2020/9/24
 * @describe：支付工具类
 */
public class PayUtil {


    /**
     * @param acticity
     * @param payParamentBean     支付所需参数
     * @param methodOfPaymentEnum
     * @param resultListener
     */
    public static void toPay(Activity acticity, IPayParament payParamentBean, MethodOfPaymentEnum methodOfPaymentEnum, PayResultListener<IPayResultBean> resultListener) {
        SoftReference<Activity> softReference = new SoftReference<>(acticity);
        if (softReference.get() != null && !softReference.get().isFinishing()) {
            IPayEntity iPayEntity = getIPayEntity(methodOfPaymentEnum);
            if (iPayEntity != null) {
                iPayEntity.pay(softReference.get(), payParamentBean, resultListener);
            } else {
                ToastUtil.showLongToast(softReference.get(), "没有找到此支付方式");
            }
        }
    }

    /**
     * 获取支付实体类
     *
     * @param methodOfPaymentEnum
     * @return
     */
    public static IPayEntity getIPayEntity(MethodOfPaymentEnum methodOfPaymentEnum) {
        IPayEntity iPayEntity = null;
        switch (methodOfPaymentEnum) {
            case ZFB_PAY://支付宝
                break;
            case WECHAT_PAY://微信
                iPayEntity = WxPay.getSingleInstant();
                break;
            case RED_ENVELOPE_PAY://红包
                break;
            case WALLET_PAY://钱包
                break;
            case YL_PAY://云闪付
                break;
            case YWT_PAY://招商银行
                break;
            case JD_PAY://京东支付
                break;
        }
        return iPayEntity;
    }
}
