package com.haier.hailicommontlib.mvp_api.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Map;

import androidx.annotation.LayoutRes;

/**
 * @author： jjf
 * @date： 2018/4/8
 * @describe：
 */
public abstract class MyBaseAdapter<C extends Context, T extends ArrayList> extends BaseAdapter {

    T arrayList;//数据
    C context;//上下文

    public MyBaseAdapter(C c, T t) {
        this.arrayList = t;
        this.context = c;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, getLayoutId(), null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            findView();
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        setValue(viewHolder, getItem(position));
        return convertView;
    }

    public static class ViewHolder {

        public final View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
            itemView.setTag(this);
        }
    }



    public abstract void findView();

    /**
     * 设置值
     */
    public abstract void setValue(ViewHolder viewHolder, Object o);

    public abstract Map<Integer, View> setViewHolderParam();

    /**
     * 条目布局
     *
     * @return
     */
    public abstract @LayoutRes
    int getLayoutId();


    /**
     * 查询控件，直接设置监听事件
     *
     * @param id
     * @param setOnClickListener
     * @param <V>
     * @return
     */
    public View findViewById(View view, int id, View.OnClickListener setOnClickListener) {
        View v =   view.findViewById(id);
        if (setOnClickListener != null) {
            v.setOnClickListener(setOnClickListener);
        }
        return v;
    }

    /**
     * 查询控件，直接设置监听事件
     *
     * @param id
     * @return
     */
    public View findViewById(View view, int id) {
        View v =   view.findViewById(id);
        return v;
    }
}
