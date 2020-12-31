package com.haier.hailicommontlib.mvp_api.moudle.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author： JiaoJunfeng
 * @date： 2018/1/15
 * @describe：所有数据类型的基本类（接口返回数据最外层模型）
 */
public class BaseData<C> implements Serializable {

    private C retData;
    private long sysTime;
    private String retCode;
    private String retInfo;
    private String retSign;
    public BaseData() {
    }

    public BaseData(C retData, long sysTime, String retCode, String retInfo, String retSign) {
        this.retData = retData;
        this.sysTime = sysTime;
        this.retCode = retCode;
        this.retInfo = retInfo;
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

    public C getData() {
        return retData;
    }

    public void setData(C retData) {
        this.retData = retData;
    }


    public void setRetData(C retData) {
        this.retData = retData;
    }

    public String getRetSign() {
        return retSign;
    }

    public void setRetSign(String retSign) {
        this.retSign = retSign;
    }

    @Override
    public String toString() {
        return "{" + "retData=" + retData + ", sysTime=" + sysTime + ", retCode='" + retCode + '\'' + ", retInfo='" + retInfo + '\'' + ", retSign='" + retSign + '\'' + '}';
    }

    /**
     * 把数据（模型）转化为Json串
     *
     * @param clazz
     * @return
     */
    public String toJson(Class<C> clazz) {
        Gson gson = new Gson();
        Type objectType = type(BaseData.class, clazz);
        return gson.toJson(this, objectType);
    }

    /**
     * 把Json串转化为模型（用Gson 实现带范型的模型的解析）
     * 解析范型用到以下两个方法 fromJson（）、type（）
     */
    public BaseData fromJson(String json, Class<C> clazz) {

        Gson gson = new Gson();
        if (clazz == null) {
            //直接解析出问题
            BaseData baseBeen = gson.fromJson(json, BaseData.class);
            return baseBeen;
        }
        Type objectType = type(BaseData.class, clazz);

        return gson.fromJson(json, objectType);
    }

    //获取范型的type
    private ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

}
