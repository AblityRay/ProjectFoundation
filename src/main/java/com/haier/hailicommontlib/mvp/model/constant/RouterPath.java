package com.haier.hailicommontlib.mvp.model.constant;

/**
 * @author： jjf
 * @date： 2020/9/11
 * @describe：路由通讯 路径格式：一级路径要使用lib的名称，后面跟着跳转的界面的具体路径（
 * 一级路径这样设置防止路径冲突，后面取项目中的相对路径，为了快捷的认出路径去向）
 */
public class RouterPath {
    /**
     * 去往洗衣列表界面
     */
    public static final String TO_LAUNDRY_LIB_SHOP_LIST = "/laundrylib/com/haier/laundrylib/view/activity/LaundryShopListActivity";
    /**
     * 主module获取laundryLib库中的类
     * 通过Arouter的暴露服务来通讯
     */
    public static final String GET_LAUNDRY_LIB_LAUNDRY_FRAGMENT = "/laundrylib/com/haier/laundrylib/view/fragment/LaundryListByDeviceTypeFragment";
    /**
     * 主module获取UserInfo库中的类
     * 通过Arouter的暴露服务来通讯
     */
    public static final String GET_USER_INFO_LIB_USER_INFO_FRAGMENT = "/userInfoLib/com/haier/userinfolib/view/fragment/UserInfoFragment";
}
