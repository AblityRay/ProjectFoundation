package com.haier.hailicommontlib.mvp.model.bean.pay;

import com.haier.hailicommontlib.mvp.model.interfaces.IPayParament;

/**
 * @author： jjf
 * @date： 2019/7/4
 * @describe：微信支付信息（请求支付前，请求关键参数）
 */
public class WxPayInfoBeen implements IPayParament {

    private String appid;    //	公众账号ID
    private String partnerid;    //	商户号
    private String prepayid;    //	预支付交易会话ID
    private String packages;    //	扩展字段
    private String noncestr;    //	随机字符串
    private String timestamp;    //	时间戳
    private String sign;    //	签名

    public WxPayInfoBeen() {
    }

    public WxPayInfoBeen(String appid, String partnerid, String prepayid, String packages, String noncestr, String timestamp, String sign) {
        this.appid = appid;
        this.partnerid = partnerid;
        this.prepayid = prepayid;
        this.packages = packages;
        this.noncestr = noncestr;
        this.timestamp = timestamp;
        this.sign = sign;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "WxPayInfoBeen{" +
                "appid='" + appid + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", packages='" + packages + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
