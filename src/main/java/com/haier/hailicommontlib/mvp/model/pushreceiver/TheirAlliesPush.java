package com.haier.hailicommontlib.mvp.model.pushreceiver;

import android.content.Context;

import com.haier.hailicommontlib.mvp.model.bean.push.PersonalInfData;
import com.haier.hailicommontlib.mvp.model.utils.LogUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;

/**
 * @author： SQW
 * @date： 2020/9/27
 * @describe：推送窗口
 */
public class TheirAlliesPush {

    private  CustomNotificationHandler customNotificationHandler;
    private  MyUmengMessageHandler myUmengMessageHandler;
    private PushAgent mPushAgent;
    /**
     * 初始化
     * @param locationContext
     */
    public TheirAlliesPush(Context locationContext){
        initPush(locationContext);
    }


    /**
     * 初始化友盟推送
     */
    public  void initPush(Context context) {
        // 初始化SDK
        UMConfigure.init(context, AppVariableChanges.UMENG_APPKEY, "Haili", UMConfigure.DEVICE_TYPE_PHONE, AppVariableChanges.UMENG_MESSAGE_SECRET);
        UMConfigure.setLogEnabled(true);
        // 选用LEGACY_AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        // 支持在子进程中统计自定义事件
        UMConfigure.setProcessEvent(true);
        //推送注册初始化
        mPushAgent = PushAgent.getInstance(context);
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);//sdk开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER); //服务端控制声音
        mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);//客户端允许呼吸灯点亮
        mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);//客户端振动
        mPushAgent.setDisplayNotificationNumber(2);//通知栏按数量显示
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                PersonalInfData.getInstance().setChannelId(deviceToken);
                PersonalInfData.getInstance().setBaiduUserId(deviceToken);
                LogUtil.D("mPushAgent", "deviceToken:" + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
            }
        });
        customNotificationHandler = new CustomNotificationHandler();
        myUmengMessageHandler = new MyUmengMessageHandler();
        mPushAgent.setNotificationClickHandler(customNotificationHandler);
        mPushAgent.setMessageHandler(myUmengMessageHandler);
    }


}
