package com.haier.hailicommontlib.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.haier.hailicommontlib.R;
import com.haier.hailicommontlib.mvp.model.AppConstant;
import com.haier.hailicommontlib.mvp.model.bean.pay.WxPayResultBean;
import com.haier.hailicommontlib.mvp.model.enums.MethodOfPaymentEnum;
import com.haier.hailicommontlib.mvp.model.interfaces.IPayResultBean;
import com.haier.hailicommontlib.mvp.model.interfaces.PayResultListener;
import com.haier.hailicommontlib.mvp.model.pay.entity.WxPay;
import com.haier.hailicommontlib.mvp.model.utils.LogUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信支付返回事件
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, AppConstant.WECHAT_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        LogUtil.D(TAG, "onPayFinish, errCode = " + resp.errCode);
        WxPayResultBean wxPayResultBean = new WxPayResultBean(resp);
        PayResultListener<IPayResultBean> payResultListener = WxPay.getSingleInstant().getPayResultListener();
        if (payResultListener != null) {
            payResultListener.payType(MethodOfPaymentEnum.WECHAT_PAY, wxPayResultBean);
        }
    }
}