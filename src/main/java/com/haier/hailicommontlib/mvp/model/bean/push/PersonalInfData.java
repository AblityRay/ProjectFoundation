package com.haier.hailicommontlib.mvp.model.bean.push;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/29.
 * 个人信息
 */

public class PersonalInfData implements Serializable {

    private String channelId;//百度推送用户channelId
    private String baiduUserId;//百度推送用户id

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getBaiduUserId() {
        return baiduUserId;
    }

    public void setBaiduUserId(String baiduUserId) {
        this.baiduUserId = baiduUserId;
    }

    private static class SingletonHolder {
        public static final PersonalInfData INSTANCE = new PersonalInfData();
    }

    public static PersonalInfData getInstance() {
        return SingletonHolder.INSTANCE;
    }



}
