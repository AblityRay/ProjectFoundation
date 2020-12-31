package com.haier.hailicommontlib.mvp.model.bean;


/**
 * @author： jjf
 * @date： 2020/9/11
 * @describe：路由传值  承载参数
 */
public class ParameterBeen<T> {
    private String key;
    private T value;

    public ParameterBeen(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
