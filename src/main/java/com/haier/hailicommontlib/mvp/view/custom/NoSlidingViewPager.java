package com.haier.hailicommontlib.mvp.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.haier.hailicommontlib.mvp.model.utils.ToastUtil;

import androidx.viewpager.widget.ViewPager;

/**
 * @author： jjf
 * @date： 2018/7/3
 * @describe：自定义Viewpager 可以设置是否左右滑动
 */
public class NoSlidingViewPager extends ViewPager {
    private Context context;

    public NoSlidingViewPager(Context context) {
        this(context, null);
    }

    public NoSlidingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }


    private boolean isSlide = false;
    /**
     * 页面切换时是否有滚功效果
     */
    private boolean haveScrollEffect = true;

    /**
     * 是否可以进行手势滑动切换页面
     *
     * @param slide
     */
    public void setSlide(boolean slide) {
        isSlide = slide;
    }

    /**
     * 页面切换时是否有滚功效果
     *
     * @param haveScrollEffect
     */
    public void setHaveScrollEffect(boolean haveScrollEffect) {
        this.haveScrollEffect = haveScrollEffect;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isSlide && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isSlide && super.onTouchEvent(ev);
    }

    @Override
    public void setCurrentItem(int item) {
        //false 去除滚动效果
        try {
            super.setCurrentItem(item, haveScrollEffect);
        } catch (Exception e) {
            ToastUtil.showShortToast(context, "e=" + e.toString());
            ToastUtil.showShortToast(getContext(), "请重新打开APP");
        }
    }
}
