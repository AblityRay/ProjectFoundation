package com.haier.hailicommontlib.mvp_api.moudle.bean;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * @author： 焦俊峰
 * @date： 2018/3/31
 * @describe：页面选择器数据模型
 */
public class SelectPageBeen implements Serializable {
    private String content;
    private int type;
    private String num;
    private Drawable drawable;
    private String param;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getContent() {
        return content;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public SelectPageBeen(String content, int type, String num) {
        this.content = content;
        this.type = type;
        this.num = num;
    }
}
