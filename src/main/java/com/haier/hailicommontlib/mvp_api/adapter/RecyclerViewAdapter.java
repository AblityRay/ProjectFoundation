package com.haier.hailicommontlib.mvp_api.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.haier.hailicommontlib.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author： jjf
 * @date： 2018/6/9
 * @describe： 简单封装RecyclerView    适配器
 */
public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public ArrayList<T> runModeBeens;

    private int mViewType;
    public static final int PARENT_BASE_HAVE_PROJECTION = 1;//有投影有内间距有点击背景切换
    public static final int PARENT_BASE_NOT_HAVE_PROJECTION_HAVE_PADDING = 3;//无投影有内间距
    public static final int PARENT_BASE_NOT_HAVE_PROJECTION = 2;//无投影无内间距
    public static final int PARENT_BASE_NOT_ALL = 4;//任何无状态

    public RecyclerViewAdapter(ArrayList<T> runModeBeens,   int mViewType) {
        this.runModeBeens = runModeBeens;
        this.mViewType = mViewType;
    }

    /**
     * 是否显示第一行间距
     *
     * @return
     */
    public abstract boolean setTopIsShow();

    public abstract View setItemView(Context context);

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View parentView;
        if (mViewType == PARENT_BASE_HAVE_PROJECTION) {
            parentView = View.inflate(parent.getContext(), R.layout.adapter_base_view, null);
        } else if (mViewType == PARENT_BASE_NOT_HAVE_PROJECTION) {//（默认格式）目前只有一种格式 所以布局用一个
            parentView = View.inflate(parent.getContext(), R.layout.adapter_base_view_not_projection, null);
        } else if (mViewType == PARENT_BASE_NOT_HAVE_PROJECTION_HAVE_PADDING) {
            parentView = View.inflate(parent.getContext(), R.layout.adapter_base_view_not_projection_have_padding, null);
        } else if (mViewType == PARENT_BASE_NOT_ALL) {
            parentView = View.inflate(parent.getContext(), R.layout.adapter_base_view_all_not, null);
        } else {
            parentView = View.inflate(parent.getContext(), R.layout.adapter_base_view, null);
        }
        View view = parentView.findViewById(R.id.v_padding);
        //是否显示顶部间距
        if (setTopIsShow()) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
        LinearLayout linearLayout = parentView.findViewById(R.id.lly_margin);
        View childview = setItemView(parent.getContext());
        if (childview != null)
            linearLayout.addView(childview);

        ViewHolder holder = new ViewHolder(parentView);
        return holder;
    }

    @Override
    public int getItemCount() {
        return runModeBeens.size();
    }

    /**
     * 实例化View对象
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private SparseArray<View> mHolderViews;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            mHolderViews = new SparseArray<>();
        }


        public void hold(int... resIds) {
            for (int id : resIds) {
                mHolderViews.put(id, view.findViewById(id));
            }
        }

        public View  get(int id) {
            return  mHolderViews.get(id);
        }
    }

}
