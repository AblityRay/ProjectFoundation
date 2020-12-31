package com.haier.hailicommontlib.mvp_api.adapter;

/**
 * @author： jjf
 * @date： 2018/5/11
 * @describe：
 */

import android.content.Context;
import android.util.SparseArray;
import android.view.View;

/**
 * 、
 * 默认 viewholder
 */
public class DefaultViewHolder extends ViewHolder {
    private SparseArray<View> mHolderViews;

    public DefaultViewHolder(Context context, View view, int viewType) {
        super(context, view, viewType);
        mHolderViews = new SparseArray<>();
    }

    public void hold(int... resIds) {
        for (int id : resIds) {
            if (mHolderViews.get(id) == null) {
                mHolderViews.remove(id);
                mHolderViews.put(id, parentView.findViewById(id));
            }
        }
    }

    public   View get(int id) {
        return   mHolderViews.get(id);
    }
}