package com.haier.hailicommontlib.mvp.model.utils.loading;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haier.hailicommontlib.R;
import com.haier.hailicommontlib.mvp_api.moudle.LoadingInterface;


/**
 * 加载中Dialog
 */
public class LoadingDialog extends AlertDialog implements LoadingInterface<String> {

    private TextView tips_loading_msg;
    private int type;
    private String message = null;
    ImageView imageView;
    AnimationDrawable animation;
    private Context context;

    /**
     * 点击屏幕外边消失
     */
    private boolean canceledOnTouchOutside = true;

    @Override
    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
    }

    /**
     * 构造方法
     *
     * @param context 上下文
     * @param type    loading 图案类型
     */
    public LoadingDialog(Context context, int type, boolean canceledOnTouchOutside) {
        super(context);
        this.canceledOnTouchOutside = canceledOnTouchOutside;
        this.context = context;
        this.type = type;
        message = context.getResources().getString(R.string.xlistview_header_hint_loading);

    }

    /**
     * 构造方法
     *
     * @param context 上下文
     * @param type    loading 图案类型
     */
    public LoadingDialog(Context context, int type, String message, boolean canceledOnTouchOutside) {
        super(context);
        this.context = context;
        this.type = type;
        this.canceledOnTouchOutside = canceledOnTouchOutside;
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //指定为自己的布局
        setContentView(R.layout.loading_circle);

        tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
        imageView = findViewById(R.id.iv_loading);
        animation = (AnimationDrawable) imageView.getBackground();
        if (message != null && message.length() > 0) {
            tips_loading_msg.setText(this.message);
        } else {
            tips_loading_msg.setVisibility(View.GONE);
        }
//         setCancelable(false);//屏蔽返回键
        setCanceledOnTouchOutside(canceledOnTouchOutside);
        Window window = this.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
            layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT;
            window.getDecorView().setPadding(0, 0, 0, 0);
            getWindow().setAttributes(layoutParams);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// 有白色背景，加这句代码
        }
    }


    @Override
    public final void show() {
        try {
            super.show();
            if (animation != null) {
                animation.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (animation != null) {
            animation.stop();
        }
    }

    public void startLoadingDialog() {
        if (!isShowing() && !((Activity) context).isFinishing())
            show();
    }

    public void stopLoadingDialog() {
        dismiss();
    }

    @Override
    public void setData(String string) {
        message = string;
        if (tips_loading_msg != null) {
            if (string != null && string.length() > 0) {
                tips_loading_msg.setText(string);
            } else {
                tips_loading_msg.setVisibility(View.GONE);
            }
        }
    }
}
