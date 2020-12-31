package com.haier.hailicommontlib.mvp.model.enums;

/**
 * @author：jjf
 * @date：2019/7/1
 * @describe：项目中目前已经集成的支付方式
 */
public enum MethodOfPaymentEnum {
    //1支付宝 2 优惠劵 3 现金 4 囍联支付 5 微信支付 6 (海尔)正元一卡通 7 红包 8钱包    9纳客宝    10银联  13快捷通 14一网通（招商银行）
    ZFB_PAY(1, "支付宝", 1, true),
    COUPONS_PAY(2, "优惠劵", 2, true),
    WECHAT_PAY(5, "微信", 1, true),
    RED_ENVELOPE_PAY(7, "红包", 2, true),
    WALLET_PAY(8, "钱包", 1, true),
//    NKB_PAY(9, "纳客宝", 2, true),
    YL_PAY(10, "云闪付", 1, true),
//    KJT_PAY(13, "海尔快捷通", 1, true),
    YWT_PAY(14, "招商银行", 1, true),
    JD_PAY(15, "京东支付", 1, true),
    IG_PAY(18, "积分", 2, true),
    NULL(0,"",0,false);
    private int _id;
    private String name;
    private int type;//1 支付方式，2优惠方式
    private boolean isOpen;


    MethodOfPaymentEnum(int _id, String name, int type, boolean isOpen) {
        this._id = _id;
        this.name = name;
        this.type = type;
        this.isOpen = isOpen;
    }

    public int get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public int getType() {
        return type;
    }

    /**
     * 通过id获取支付方式
     *
     * @param _id
     * @return
     */
    public static MethodOfPaymentEnum getMethodOfPaymentEnumById(int _id) {
        for (MethodOfPaymentEnum methodOfPaymentEnum : MethodOfPaymentEnum.values()) {
            if (methodOfPaymentEnum.get_id() == _id) {
                return methodOfPaymentEnum;
            }
        }
        return NULL;
    }
}
