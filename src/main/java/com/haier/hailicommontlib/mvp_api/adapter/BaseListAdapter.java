/*
 * Date: 14-11-21
 * Project: Access-Control-TV
 */
package com.haier.hailicommontlib.mvp_api.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.haier.hailicommontlib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 进行简单封装的列表适配器
 **/
public class BaseListAdapter<T, H extends ViewHolder> extends BaseAdapter {
    private final List<T> mData;
    private final ViewCreator<T, H> mViewCreator;
    private Context context;

    public BaseListAdapter(Context context, ViewCreator<T, H> creator) {
        this(context, new ArrayList<T>(), creator);
    }

    public BaseListAdapter(Context context, List<T> data, ViewCreator<T, H> creator) {
        mData = data == null ? new ArrayList<T>() : data;
        mViewCreator = creator;
        this.context = context;
    }

    String TAG = "BaseListAdapter";

    @Override
    public int getCount() {

        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = mViewCreator.createHolder(position, parent);
            convertView = holder.parentView;
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        View view = convertView.findViewById(R.id.v_padding);
        if (view != null) {
            if (position == 0) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }
        mViewCreator.bindData(context, position, holder, getItem(position));
        return convertView;
    }

    /**
     * 刷新适配器
     *
     * @param data
     */
    public void update(List<T> data) {
        mData.clear();
        addData(data);
    }

    /**
     * 添加新数据
     *
     * @param data
     */
    public void addData(List<T> data) {
        if (data != null) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

}
