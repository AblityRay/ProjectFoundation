package com.haier.hailicommontlib.mvp.model.provider;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.haier.hailicommontlib.mvp_api.presenter.IPresenter;

import androidx.fragment.app.Fragment;

/**
 * @author： jjf
 * @date： 2020/9/14
 * @describe：获取laundryLib的服务或某个类
 */
public interface LaundryLibPrivoder extends IProvider {
    /**
     * 获取库里面的某个碎片
     * @return
     */
    Fragment creatFragment();

    /**
     * 获取控制器
     * @return
     */
    IPresenter getIPresenter();
}
