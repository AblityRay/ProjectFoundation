package com.haier.hailicommontlib.mvp_api.moudle;

/**
 * @author： 焦俊峰
 * @date： 2018/3/22
 * @describe：
 */
public interface LoadingInterface<T> {
    /**
     * 现实动画
     */
    void startLoadingDialog();

    /**
     * 隐藏动画
     */
    void stopLoadingDialog();

    /**
     * 设置参数
     */
    void setData(T t);
}
