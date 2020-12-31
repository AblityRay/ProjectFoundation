package com.haier.hailicommontlib.mvp.model.utils;

/**
 * 点击检测工具类
 */
public class SingleClickUtil {

    private String TAG = "SingleClickUtil";

    private static boolean isEnableToClick = true;

    /**
     * 判断是否可以进行点击
     *
     * @return 真：可以点击，并且之后根据点击时间是否是延时操作调用响应开始函数 假：不可以点击
     */
    public static boolean isEnableToClick() {
        return isEnableToClick;
    }

    public static void setEnableToClick(boolean isEnable) {
        isEnableToClick = isEnable;
    }

    /**
     * 非延时的点击事件开始函数
     */
    public void click() {
        setEnableToClick(false);
        new Thread(new MyThread()).start();
        LogUtil.I(TAG, "Click is over");
    }

    /**
     * 延时的点击事件开始函数
     */
    public void clickUntilCancle() {
        setEnableToClick(false);
    }

    /**
     * 延时点击事件结束函数
     */
    public void cancle() {
        setEnableToClick(true);
    }

    private class MyThread implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setEnableToClick(true);
        }

    }
}
