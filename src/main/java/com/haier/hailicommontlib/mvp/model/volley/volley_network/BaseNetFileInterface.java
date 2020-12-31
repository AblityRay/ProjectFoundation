package com.haier.hailicommontlib.mvp.model.volley.volley_network;

/**
 * @author：焦俊峰
 * @date：2017/12/14
 * @describe： 请求网络返回
 */

public interface BaseNetFileInterface<T>{
    /**
     * 网络请求成功
     */
    void success(T object);

    /**
     * 网络请求失败
     */
    void filed(Exception e);

    void onProgress(int bytesWritten, int totalSize);
}
