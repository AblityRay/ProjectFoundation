package com.haier.hailicommontlib.mvp_api.moudle;

/**
 * @author： 宋庆伟
 * @date： 2020/09/18
 * @describe：需要证书验签初始化时的数据类型的基本类
 */
public class BaseSignCateData {
    private long sysTime;
    private String retCode;
    private String retInfo;
    private String retData;
    private String retSign;

    public String getRetData() {
        return retData;
    }

    public void setRetData(String retData) {
        this.retData = retData;
    }

    public String getRetSign() {
        return retSign;
    }

    public void setRetSign(String retSign) {
        this.retSign = retSign;
    }

    public long getSysTime() {
        return sysTime;
    }

    public void setSysTime(long sysTime) {
        this.sysTime = sysTime;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetInfo() {
        return retInfo;
    }

    public void setRetInfo(String retInfo) {
        this.retInfo = retInfo;
    }

    @Override
    public String toString() {
        return "{" +
                "\"sysTime\":" + sysTime +
                ", \"retCode\":\"" + retCode + '\"' +
                ", \"retInfo\":\"" + retInfo + '\"' +
                ", \"retData\":\"" + retData + '\"' +
                ", \"retSign\":\"" + retSign + '\"' +
                '}';
    }
}
