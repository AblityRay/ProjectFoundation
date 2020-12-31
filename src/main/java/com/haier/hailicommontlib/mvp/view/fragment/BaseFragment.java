package com.haier.hailicommontlib.mvp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.haier.hailicommontlib.R;
import com.haier.hailicommontlib.mvp.model.utils.LogUtil;
import com.haier.hailicommontlib.mvp.view.activity.BaseActivity;
import com.haier.hailicommontlib.mvp_api.presenter.IPresenter;
import com.haier.hailicommontlib.mvp_api.view.Iview;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author： 焦俊峰
 * @date： 2018/3/22
 * @describe：
 */
public abstract class BaseFragment extends Fragment implements Iview, View.OnClickListener {
    IPresenter iPresenter;
    private String TAG = "BaseFragment";
    private int layoutId;
    private View view;

    View notNetWork;
    View notData;
    private BaseActivity activity;
    ArrayList<View> views = new ArrayList<>();//用于暂无数据与从新加载页面显示
    private TextView tvNotdataMessage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.I("BaseActivity", "" + getClass().getSimpleName());

        layoutId = getLayoutId();
        activity = (BaseActivity) getActivity();
        //获取本view层对应的P层
        iPresenter = bindPresenter();
        if (iPresenter != null) {
            iPresenter.setView(activity);
        }
        view = View.inflate(activity, layoutId, null);

        View parentView = View.inflate(activity, R.layout.fragment_base, null);
        //添加无数据以及无网络UI
        notNetWork = parentView.findViewById(R.id.utiles_reload_the);
        notData = parentView.findViewById(R.id.utiles_temporarily_no_data);
        tvNotdataMessage = parentView.findViewById(R.id.tv_notdata_message);
        TextView textView = notNetWork.findViewById(R.id.reload_btn);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPage();
            }
        });
        FrameLayout fly = parentView.findViewById(R.id.fly_);
        fly.addView(view);
        addDefultView(notData, notNetWork);
        initView();

        return parentView;
    }


    /**
     * 如果参数为null，则隐藏控件 如果不想改变默认值则传""
     *
     * @param content
     */
    public void notDataPageMessageShow(String content) {

        if (content == null) {
            tvNotdataMessage.setVisibility(View.GONE);
        } else {
            tvNotdataMessage.setVisibility(View.VISIBLE);
            if (content.length() > 0) {
                tvNotdataMessage.setText(content);
            }
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 查询控件，直接设置监听事件
     *
     * @param id
     * @param setOnClickListener
     * @param <V>
     * @return
     */
    public <V extends View> V findViewById(int id, boolean setOnClickListener) {
        V v = (V) view.findViewById(id);
        if (setOnClickListener) {
            v.setOnClickListener(this);
        }
        return v;
    }

    /**
     * 查询控件
     *
     * @param id
     * @param <V>
     * @return
     */
    public <V extends View> V findViewById(int id) {
        V v = (V) view.findViewById(id);
        return v;
    }


    //与本UI层对应的一个P层
    public abstract IPresenter bindPresenter();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 添加点击事件以及用户交互逻辑
     */
    public abstract void initData();

    /**
     * 返回当前视图需要的layout的id
     *
     * @return
     */
    public abstract int getLayoutId();


    /**
     * 添加无网络以及无数据View
     */
    public void addDefultView(View... view) {
        views.clear();
        View[] view1 = view;
        for (int i = 0; i < view1.length; i++) {
            if (view1[i].getId() == R.id.utiles_temporarily_no_data) {
                NOT_DATA = i;
            } else if (view1[i].getId() == R.id.utiles_reload_the) {
                NOT_NETWORK = i;
            }
            views.add(view1[i]);

        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();


    }

    public int NOT_DATA = 0;
    public int NOT_NETWORK = 1;
    public int INIT_VIEW = 2;

    /**
     * 根据页面状态选择View 的显示
     * 输入  -1   全部隐藏
     *
     * @param position
     */
    public void dataStute(View xlvBill, int position) {


        if (views.size() > position) {
            for (int i = 0; i < views.size(); i++) {
                if (xlvBill != null) {
                    if (position == NOT_NETWORK) {
                        xlvBill.setVisibility(View.GONE);
                    } else if (!xlvBill.isShown()) {
                        xlvBill.setVisibility(View.VISIBLE);
                    }
                }
                if (i == position) {
                    views.get(i).setVisibility(View.VISIBLE);
                } else {
                    views.get(i).setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.I(TAG, "onDestroy=" + this.toString());
    }
}
