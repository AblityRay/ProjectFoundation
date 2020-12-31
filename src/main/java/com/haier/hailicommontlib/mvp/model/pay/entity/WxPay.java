package com.haier.hailicommontlib.mvp.model.pay.entity;

import android.content.Context;

import com.haier.hailicommontlib.mvp.model.AppConstant;
import com.haier.hailicommontlib.mvp.model.interfaces.IPayEntity;
import com.haier.hailicommontlib.mvp.model.interfaces.IPayParament;
import com.haier.hailicommontlib.mvp.model.interfaces.IPayResultBean;
import com.haier.hailicommontlib.mvp.model.interfaces.PayResultListener;
import com.haier.hailicommontlib.mvp.model.utils.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @author： jjf
 * @date： 2020/9/24
 * @describe：微信支付
 */
public class WxPay implements IPayEntity {

    @Override
    public void pay(Context context, IPayParament iPayParament, PayResultListener<IPayResultBean> payResultListener) {
        this.payResultListener = payResultListener;
        // 调用SDK
        // TODO:  call Wechat's result method
        msgApi = WXAPIFactory.createWXAPI(context, AppConstant.WECHAT_APP_ID, false);
        req = new PayReq();

//        req.appId = wxPayInfoBeen.getAppid();
//        req.partnerId = wxPayInfoBeen.getPartnerid();
//        req.nonceStr = wxPayInfoBeen.getNoncestr();
//        req.packageValue = "Sign=WXPay";
//        req.prepayId = wxPayInfoBeen.getPrepayid();
//        req.timeStamp = wxPayInfoBeen.getTimestamp();
//        req.sign = wxPayInfoBeen.getSign();
        req.appId = AppConstant.WECHAT_APP_ID;
        req.partnerId = "";
        req.nonceStr = "";
        req.packageValue = "Sign=WXPay";
        req.prepayId = "";
        req.timeStamp = "";
        req.sign = "";
        if (!msgApi.sendReq(req)) {
            ToastUtil.showLongToast(context,"调用微信支付失败,检查您是否安装微信");
        }



        //模拟调用回调
//        WxPayResultBean wxPayResultBean = new WxPayResultBean("支付成功");
//
//        payResultListener.payType(MethodOfPaymentEnum.WECHAT_PAY, wxPayResultBean);
      

    }



    /**
     * 销毁资源
     */
    @Override
    public void onDestroy(){
        payResultListener=null;
        wxPay=null;
        msgApi=null;
        req=null;
    }

    private PayResultListener<IPayResultBean> payResultListener;

    private IWXAPI msgApi;
    private PayReq req;
    private static WxPay wxPay;
    public static WxPay getSingleInstant(){
        if(wxPay==null){
            wxPay=new WxPay();
        }
        return wxPay;
    }

    public PayResultListener<IPayResultBean> getPayResultListener() {
        return payResultListener;
    }
}
