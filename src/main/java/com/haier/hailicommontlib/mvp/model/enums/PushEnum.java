package com.haier.hailicommontlib.mvp.model.enums;

/**
 * @author： SQW
 * @date： 2019/7/16
 * @describe：
 */
public enum PushEnum {

    HRPushTypePay(1,"支付提醒"),
    HRPushTypeNeedToAppointment(2," 预约提醒"),
    HRPushTypeWhenWashing(3," 洗衣中提醒"),
    HRPushTypeWashFinished(4," 洗衣完成提醒"),
    HRPushTypeDryFinished(5," 烘干完成提醒"),
    HRPushTypeFaultWhenUsing(6," 使用中故障提醒"),
    HRPushTypeFaultWhenOccuping(7," 被占用故障提醒"),
    @Deprecated
    HRPushTypeAddToQueueSuccessed(8," 加入排队成功提醒"),
    HRPushTypeBroardCast(9," 海尔推送的广播同时也是海狸公告"),
    HRPushTypeNeedToStartDryDeviceInFiveMinutes(10," 5分钟之内启动干衣机的提醒"),
    HRPushTypeNeedToStartDryDeviceInTwoMinutes(11," 2分钟之内启动干衣机的提醒"),
    HRPushTypeCleanFinished(12," 桶清洁完成提醒"),
    HRPushTypeOccupied(13," 被占用故障提醒"),
    HRPushTypeDeliveryOrderRelated(14," 这是上门服务的相关推送，接收到这个推送之后，进入上门服务订单详情界面"),
    HRPushTypeMessageCenter(15," 消息中心"),
    HRPushTypeJumpFeatures(16," 特色频道"),
    HRPushTypeJumpHIStarDetile(17," hi星球分享功能");

    private int pushNumberId;
    private String name;

    PushEnum(int pushNumberId, String name) {
        this.pushNumberId = pushNumberId;
        this.name = name;
    }

    public int getPushNumberId() {
        return pushNumberId;
    }

    public void setPushNumberId(int pushNumberId) {
        this.pushNumberId = pushNumberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param pushNumberId
     * @return
     */
    public static PushEnum getPushEnumPushNumberId(int pushNumberId) {
        for (PushEnum pushEnum : PushEnum.values()) {
            if (pushEnum.getPushNumberId() == pushNumberId) {
                return pushEnum;
            }
        }
        return null;
    }
}
