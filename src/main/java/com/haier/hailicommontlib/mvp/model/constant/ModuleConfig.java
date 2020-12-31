package com.haier.hailicommontlib.mvp.model.constant;

/**
 * @author： jjf
 * @date： 2020/9/16
 * @describe：记录 《作用相当于主 module中的Application的功能类》 路径
 */
public class ModuleConfig {
    private static final String hailiMainInit = "com.haier.hailimain.HomeApplication";
    private static final String laundryLibInit = "com.haier.laundrylib.LaundryApplication";
    private static final String userInfoLibInit = "com.haier.userinfolib.UserInfoApplication";
    public static String[] moduleInits = {
            hailiMainInit,
            laundryLibInit,
            userInfoLibInit
    };
}
