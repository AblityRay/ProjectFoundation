package com.haier.hailicommontlib.mvp.model.bean.push;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * @author：宋庆伟
 * @date： 2020/9/25
 * @describe：友盟推送类
 */

public class PushBeen implements Serializable {
    private String queueId;
    private String baiduUserId;
    private String orderId;
    private String typeId;
    private String message;
    private String userId;
    private String channelId;
    private String weburl;


    public static PushBeen objectFromData(String str) {
        return new Gson().fromJson(str, PushBeen.class);
    }


    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public String getBaiduUserId() {
        return baiduUserId;
    }

    public void setBaiduUserId(String baiduUserId) {
        this.baiduUserId = baiduUserId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PushBeen{" +
                "queueId='" + queueId + '\'' +
                ", baiduUserId='" + baiduUserId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", typeId='" + typeId + '\'' +
                ", message='" + message + '\'' +
                ", userId='" + userId + '\'' +
                ", channelId='" + channelId + '\'' +
                ", weburl='" + weburl + '\'' +
                '}';
    }
}
