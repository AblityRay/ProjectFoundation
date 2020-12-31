package com.haier.hailicommontlib.mvp_api.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.haier.hailicommontlib.R;


/**
 * @author： jjf
 * @date： 2018/5/11
 * @describe：条目适配复用器
 */
public abstract class ViewHolder {
    public View parentView;
    public static final int PARENT_BASE_HAVE_PROJECTION = 1;//有投影有内间距有点击背景切换
    public static final int PARENT_BASE_NOT_HAVE_PROJECTION_HAVE_PADDING = 3;//无投影有内间距
    public static final int PARENT_BASE_NOT_HAVE_PROJECTION = 2;//无投影无内间距
    public static final int PARENT_BASE_NOT_ALL = 4;//任何无状态


    /**
     * @param context
     * @param itemView
     * @param viewType 表示list view条目的容器选择
     */
    public ViewHolder(Context context, View itemView, int viewType) {
        if (viewType == PARENT_BASE_HAVE_PROJECTION) {
            parentView = View.inflate(context, R.layout.adapter_base_view, null);
        } else if (viewType == PARENT_BASE_NOT_HAVE_PROJECTION) {//（默认格式）目前只有一种格式 所以布局用一个
            parentView = View.inflate(context, R.layout.adapter_base_view_not_projection, null);
        } else if (viewType == PARENT_BASE_NOT_HAVE_PROJECTION_HAVE_PADDING) {
            parentView = View.inflate(context, R.layout.adapter_base_view_not_projection_have_padding, null);
        }else if(viewType==PARENT_BASE_NOT_ALL){
            parentView = View.inflate(context, R.layout.adapter_base_view_all_not, null);
        } else {
            parentView = View.inflate(context, R.layout.adapter_base_view, null);
        }
        LinearLayout linearLayout = parentView.findViewById(R.id.lly_margin);
        linearLayout.addView(itemView);
        parentView.setTag(this);
    }
}