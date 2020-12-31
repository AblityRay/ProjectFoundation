package com.haier.hailicommontlib.mvp.model.bean;

import java.io.Serializable;

import androidx.annotation.NonNull;

/**
 * @author： jjf
 * @date： 2020/9/16
 * @describe：
 */
public class DeviceBean  implements  Serializable{
    private String name;

    public DeviceBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return "DeviceBean{" +
                "name='" + name + '\'' +
                '}';
    }
}
