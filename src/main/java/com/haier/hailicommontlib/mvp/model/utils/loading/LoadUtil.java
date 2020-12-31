package com.haier.hailicommontlib.mvp.model.utils.loading;

import android.content.Context;

import com.haier.hailicommontlib.R;
import com.haier.hailicommontlib.mvp.model.utils.LogUtil;
import com.haier.hailicommontlib.mvp_api.moudle.LoadingInterface;


/**
 * @author： 焦俊峰
 * @date： 2018/1/12
 * @describe：
 */
public class LoadUtil {

    public static final int LOAD_DEFULT = 1;
    public static final int LOAD_ZERO = 0;//不弹Loading

    private Context context;
    private LoadingDialog loadingDialog;
    private LoadingInterface loadingInterface;


    /**
     * 每次请求接口都会传入loading类型  每次都会去判断用的哪个loading
     * 初始化所有类型loading，等待V端去选择
     *
     * @param context
     * @param type    loading 类型  目前一种
     */
    public LoadUtil(Context context, int type) {
        this(context, type, false);
    }

    /**
     * 每次请求接口都会传入loading类型  每次都会去判断用的哪个loading
     * 初始化所有类型loading，等待V端去选择
     * <p>
     * 默认展示文本{@link R.string#xlistview_header_hint_loading}
     *
     * @param context
     * @param type                   loading 类型
     * @param canceledOnTouchOutside 点击屏幕外面是否消失
     */
    public LoadUtil(Context context, int type, boolean canceledOnTouchOutside) {
        this(context, type, context.getResources().getString(R.string.xlistview_header_hint_loading), canceledOnTouchOutside);
    }

    /**
     * @param context
     * @param type                   loading 类型
     * @param message                loading 展示的文本,如果不想展示文本，则传入null
     * @param canceledOnTouchOutside 点击外部是否可以取消
     */
    public LoadUtil(Context context, int type, String message, boolean canceledOnTouchOutside) {
        this.context = context;
        switch (type) {
            case LoadUtil.LOAD_DEFULT://显示默认类型
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog(context, type, canceledOnTouchOutside);
                }
                loadingDialog.setData(message);
                loadingInterface = loadingDialog;
                break;
            default:
                loadingInterface = null;
                break;
        }
    }

    public void showLoading() {
        LogUtil.I("LoadUtil", "showLoading1");
        if (loadingInterface != null) {
            LogUtil.I("LoadUtil", "showLoading2");
            loadingInterface.startLoadingDialog();
        }
    }

    public void highLoading() {
        if (loadingInterface != null) {
            loadingInterface.stopLoadingDialog();
        }
    }

}
