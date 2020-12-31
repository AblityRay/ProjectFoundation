package com.haier.hailicommontlib.mvp_api.moudle.bean;


import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * @author： 焦俊峰
 * @date： 2018/1/23
 * @describe：
 */
public class BaseListDate<C> implements Serializable {
    private String retCode;
    private String retInfo;
    private ArrayList<C> retData;
    private long sysTime;

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

    public ArrayList<C> getData() {
        return retData;
    }

    public void setData(ArrayList<C> retData) {
        this.retData = retData;
    }

    @Override
    public String toString() {
        return "BaseListDate{" +
                "retCode='" + retCode + '\'' +
                ", retInfo='" + retInfo + '\'' +
                ", retData=" + retData +
                '}';
    }

    /**
     * 把数据（模型）转化为Json串
     *
     * @param s
     * @param clazz
     * @return
     */
    public String toJson(String s, Class<C> clazz) {
        Gson gson = new Gson();
        Type objectType = type(BaseData.class, clazz);
        return gson.toJson(this, objectType);
    }

    /**
     * 把Json串转化为模型（用Gson 实现带范型的模型的解析）
     * 解析范型用到以下两个方法 fromJson（）、type（）
     */
    public BaseListDate<C> fromJson(String json, Class<C> c) {
        Gson gson = new Gson();
        Type objectType = type(BaseListDate.class, c);
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
