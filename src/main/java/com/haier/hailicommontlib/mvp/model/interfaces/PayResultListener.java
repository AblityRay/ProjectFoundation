package com.haier.hailicommontlib.mvp.model.interfaces;

import com.haier.hailicommontlib.mvp.model.enums.MethodOfPaymentEnum;

/**
 * @author： jjf
 * @date： 2020/9/24
 * @describe：支付回调
 *
 *  @param <T> 支付返回参数类型
 */

public interface PayResultListener<T extends IPayResultBean> {
    /**
     * 返回支付方式
     *
     * @param methodOfPaymentEnum
     */
    void payType(MethodOfPaymentEnum methodOfPaymentEnum,T request);


}
