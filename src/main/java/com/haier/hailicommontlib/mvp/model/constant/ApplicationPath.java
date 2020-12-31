package com.haier.hailicommontlib.mvp.model.constant;

/**
 * @author： jjf
 * @date： 2020/9/11
 * @describe：路由通讯 路径格式：一级路径要使用lib的名称，后面跟着Application名称
 */
public class ApplicationPath {

    public static final String MAIN_APPLIACTION = "/application_hailiMain/HomeApplication";
    public static final String LAUINDRY_LIB_APPLICATION = "/application_laundrylib/LaundryApplication";
    public static final String USER_INFO_LIB_APPLICATION = "/application_userInfoLib/UserInfoApplication";
    public static final String BLUETOOTH_LIB_APPLICATION = "/application_bluetoothLib/BluetoothApplication";
    //用来遍历获取上面的属性值
    public static final String[] paths = {MAIN_APPLIACTION,
            LAUINDRY_LIB_APPLICATION,
            USER_INFO_LIB_APPLICATION, BLUETOOTH_LIB_APPLICATION
    };
}
