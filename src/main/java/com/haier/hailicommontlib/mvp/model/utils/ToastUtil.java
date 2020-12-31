package com.haier.hailicommontlib.mvp.model.utils;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.haier.hailicommontlib.R;

import java.lang.ref.SoftReference;


/**
 * Created by yanfa1 on 2015/12/21.
 */
public class ToastUtil {

    private static final String TAG = "ToastUtil";
    private static Toast mToast;

    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            if (mToast != null) {
                mToast.cancel();
                mToast = null;//toast隐藏后，将其置为null
            }
        }
    };

    public static void showShortToast(Context context, String message) {
        if (context == null) {
            LogUtil.I(TAG, "context==null");
            return;
        }
        mHandler.removeCallbacks(r);
        SoftReference<Context> contextSoftReference = new SoftReference<Context>(context);
        if (contextSoftReference.get() == null) {
            LogUtil.I(TAG, "showShortToast2");
            return;
        }
        mToast = Toast.makeText(contextSoftReference.get(), message, Toast.LENGTH_SHORT);
        LayoutInflater inflater = (LayoutInflater) contextSoftReference.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.hailicommonlib_custom_toast, null);//自定义布局
        TextView text = (TextView) view.findViewById(R.id.toast_message);//显示的提示文字        mHandler.removeCallbacks(r);

        text.setText(message);
        mToast.setGravity(Gravity.CENTER, 0, 150);
        mToast.setView(view);

        mToast.show();
        mHandler.postDelayed(r, 1500);//延迟1秒隐藏toast
    }
    public static void showLongToast(Context context, String message, View.OnClickListener ...onClickListener) {
        if (context == null) {
            LogUtil.I(TAG, "showShortToast1");
            return;
        }
        mHandler.removeCallbacks(r);
        SoftReference<Context> contextSoftReference = new SoftReference<Context>(context);
        if (contextSoftReference.get() == null) {
            LogUtil.I(TAG, "showShortToast2");
            return;
        }
        mToast = Toast.makeText(contextSoftReference.get(), message, Toast.LENGTH_LONG);

        LayoutInflater inflater = (LayoutInflater) contextSoftReference.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.hailicommonlib_custom_toast, null);//自定义布局
        TextView text = (TextView) view.findViewById(R.id.toast_message);//显示的提示文字        mHandler.removeCallbacks(r);
        if(onClickListener!=null&&onClickListener.length>0){
            view.setOnClickListener(onClickListener[0]);
            text.setOnClickListener(onClickListener[0]);
        }
        text.setText(message);
        mToast.setGravity(Gravity.CENTER, 0, 150);
        mToast.setView(view);

        mToast.show();
        mHandler.postDelayed(r, 1500);//延迟1秒隐藏toast
    }

}