package com.haier.hailicommontlib.mvp.model.pay.entity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.haier.hailicommontlib.mvp.model.AppConstant;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 动态注册微信
 */
public class AppRegister extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		final IWXAPI api = WXAPIFactory.createWXAPI(context, null,false);

		// 将该app注册到微信
		api.registerApp(AppConstant.WECHAT_APP_ID);
	}
}
