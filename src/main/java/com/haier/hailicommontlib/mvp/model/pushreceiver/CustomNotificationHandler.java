package com.haier.hailicommontlib.mvp.model.pushreceiver;

import android.content.Context;
import android.content.Intent;

import com.haier.hailicommontlib.mvp.model.bean.push.PushBeen;
import com.haier.hailicommontlib.mvp.model.enums.PushEnum;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import java.util.Objects;

/**
 * @author： SQW
 * @date： 2020/8/12
 * @describe：友盟推送
 */
public class CustomNotificationHandler extends UmengNotificationClickHandler {
    private static final String TAG = CustomNotificationHandler.class.getName();
    private PushBeen pushBeen = null;
    private Intent intent = null;
    @Override
    public void dismissNotification(Context context, UMessage msg) {
        super.dismissNotification(context, msg);
    }


    /**
     * 打开app
     * @param context
     * @param uMessage
     */
    @Override
    public void launchApp(Context context, UMessage uMessage) {
        super.launchApp(context, uMessage);
    }


    @Override
    public void openActivity(Context context, UMessage msg) {
        super.openActivity(context, msg);
    }

    @Override
    public void openUrl(Context context, UMessage msg) {
        super.openUrl(context, msg);
    }

    /**
     * 打开自定义内容
     * @param context
     * @param msg
     */
    @Override
    public void dealWithCustomAction(Context context, UMessage msg) {
        super.dealWithCustomAction(context, msg);
        pushBeen = PushBeen.objectFromData(msg.custom);
        switch (Objects.requireNonNull(PushEnum.getPushEnumPushNumberId(Integer.parseInt(pushBeen.getTypeId())))){
            case HRPushTypePay://支付提醒 1
                //验证及支付界面
            case HRPushTypeNeedToAppointment://预约提醒2
            case HRPushTypeWhenWashing://洗衣中提醒3
            case HRPushTypeWashFinished://洗衣完成提醒4
            case HRPushTypeDryFinished://烘干完成提醒5
            case HRPushTypeFaultWhenUsing: //使用中故障提醒6
            case HRPushTypeFaultWhenOccuping://被占用故障提醒7
            case HRPushTypeOccupied://被占用故障提醒13
                //订单详情界面
            case HRPushTypeCleanFinished://桶清洁完成提醒12
            case HRPushTypeDeliveryOrderRelated://这是上门服务的相关推送，接收到这个推送之后，进入上门服务订单详情界面14
            case HRPushTypeMessageCenter://消息中心15
            case HRPushTypeJumpFeatures://特色频道16
            case HRPushTypeJumpHIStarDetile://hi星球分享功能17
            case HRPushTypeBroardCast://海尔推送的广播同时也是海狸公告9
            case HRPushTypeAddToQueueSuccessed://加入排队成功提醒8
            case HRPushTypeNeedToStartDryDeviceInFiveMinutes://5分钟之内启动干衣机的提醒10
            case HRPushTypeNeedToStartDryDeviceInTwoMinutes://2分钟之内启动干衣机的提醒11
                //不处理
                break;
        }

    }





    @Override
    public void autoUpdate(Context context, UMessage msg) {
        super.autoUpdate(context, msg);
    }


}
