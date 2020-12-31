package com.haier.hailicommontlib.mvp_api.presenter;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * @author：焦俊峰
 * @date：2017/12/21
 * @describe： Handler 基本处理
 */

public class IHandler<T> extends Handler {

    //软引用持有对象
    WeakReference<T> tWeakReference;

    private static final int CANCEL_LOOPER = 0 * 1000000;
    private static final int FAILE = 0 * 1000001;


    public IHandler(T t) {
        tWeakReference = new WeakReference<>(t);
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };



}
