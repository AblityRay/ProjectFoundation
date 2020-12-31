package com.haier.hailicommontlib.mvp_api.adapter;

import android.content.Context;
import android.view.ViewGroup;

/**
 * @author： jiaojunfeng
 * @date： 2018/5/11
 * @describe：adapter 控制器规范
 */
public interface ViewCreator<T, H extends ViewHolder> {

    H createHolder(int position, ViewGroup parent);

    /**
     * 设置列表里的视图内容
     *
     * @param position 在列表中的位置
     * @param holder   该位置对应的视图
     */
    void bindData(Context context, int position, ViewHolder holder, T data);
}