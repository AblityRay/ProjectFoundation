package com.haier.hailicommontlib.mvp.persenter;

import com.haier.hailicommontlib.mvp.view.activity.BaseActivity;
import com.haier.hailicommontlib.mvp.view.fragment.BaseFragment;
import com.haier.hailicommontlib.mvp_api.presenter.IPresenter;
import com.haier.hailicommontlib.mvp_api.view.Iview;

/**
 * @author： jjf
 * @date： 2020/9/25
 * @describe：
 */
public class BaseIPresenter implements IPresenter {
    public BaseActivity baseActivity;

    @Override
    public void setView(Iview view) {
        if (view instanceof BaseFragment) {
            baseActivity = (BaseActivity) ((BaseFragment) view).getActivity();
        } else {
            baseActivity = (BaseActivity) view;
        }
    }

}
