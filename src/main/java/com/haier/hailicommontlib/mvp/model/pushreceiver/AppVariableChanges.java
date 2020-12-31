package com.haier.hailicommontlib.mvp.model.pushreceiver;

import com.haier.hailicommontlib.BaseApplication;

/**
 * @author： SQW
 * @date： 2020/9/14
 * @describe： 多数变量修改
 */
public class AppVariableChanges {


    //友盟推送
    public static final String UMENG_MESSAGE_SECRET_YES = "5798252457e6dca10aa689b2cab86ea3";// //友盟推送 Umeng Message Secret 正式
    public static final String UMENG_MESSAGE_SECRET_TEXT ="282f6137a22dd11b01aa504e0984be55";// //友盟推送 Umeng Message Secret 测试

    public static final String UMENG_APPKEY_YES = "5f6c17e046549c54f0b6bcc0";// //友盟推送 Umeng appk  正式
    public static final String UMENG_APPKEY_TEXT ="5f6ff77980455950e4982b7a";// //友盟推送 Umeng appk  测试


    public static final String UMENG_MESSAGE_SECRET = BaseApplication.UMENG_CHEnnID ? UMENG_MESSAGE_SECRET_YES : UMENG_MESSAGE_SECRET_TEXT;
    public static final String UMENG_APPKEY = BaseApplication.UMENG_CHEnnID ? UMENG_APPKEY_YES : UMENG_APPKEY_TEXT;
}
