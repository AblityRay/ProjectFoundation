package com.haier.hailicommontlib.mvp.model.volley.volley_network;

import android.content.Context;

import com.haier.hailicommontlib.ApplicationUtils;
import com.haier.hailicommontlib.mvp.model.AppConstant;
import com.haier.hailicommontlib.mvp.model.utils.LogUtil;
import com.haier.hailicommontlib.mvp.model.utils.ToastUtil;
import com.haier.hailicommontlib.mvp.model.utils.loading.LoadUtil;
import com.haier.hailicommontlib.mvp.model.utils.signutil.DesUtil;
import com.haier.hailicommontlib.mvp.model.utils.signutil.SignLexelUtil;
import com.haier.hailicommontlib.mvp.model.utils.signutil.TimeUtils;
import com.haier.hailicommontlib.mvp.model.utils.signutil.WashCallApiSignSecurity;
import com.haier.hailicommontlib.mvp.model.volley.DefaultRetryPolicy;
import com.haier.hailicommontlib.mvp.model.volley.Request;
import com.haier.hailicommontlib.mvp_api.moudle.bean.BaseData;
import com.haier.hailicommontlib.mvp_api.moudle.bean.BaseListDate;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static com.haier.hailicommontlib.mvp.model.AppConstant.UTF_8;

/**
 * @author：宋庆伟
 * @date：2020/9/17
 * @describe：本类实现网络请求的具体方法，但是强制使用者自己实现请求结果失败、成功的回调方法
 */

public abstract class RequestNetWork<T> implements BaseNetWorkInterface<T> {

    //    接口是否加密
    public static final boolean IS_TO_RSA = false;

    private Context context;
    private final String TAG = "RequestNetWork";
    LoadUtil loadUtil;

    /**
     * 低级加密接口
     * @param loadType
     * @param context
     * @param url
     * @param map
     * @param childClass
     * @return
     */
    public GsonRequest<T> getXCList(int loadType, Context context, String url, Map<String, String> map, Class childClass ) {
       return getXCList(loadType,context,url,null,map,childClass );
    }


    /**
     * 高级密码方式
     * 请求网络数（data）为集合
     * isNewUrl  true 是新接口
     *
     * @param loadType 0 没有loading  1 普通通用loading
     */
    public GsonRequest<T> getXCList(int loadType, Context context, String url, String method, Map<String, String> map, Class childClass ) {
        Map<String, String> paramMap = new HashMap<>();
        try {
            if (context != null) {
                this.context = context;
                load(context, loadType);
            }
            if (map == null) {
                map = new HashMap<>();
            } else {
                //对参数进行加密、签名
                if (method==null){
                    //低级
                    paramMap = paramentRSA(map);
                }else {
                    //高级
                    paramMap = paramentRSA(map, method);
                }
            }
            int position = 0;
            for (String key : paramMap.keySet()) {
                    position++;
                    url += (position == 1) ? ("?") : ("&");
                    url += key + "=" + map.get(key);
                    LogUtil.I("RequestNetWork", "key=" + key + "--map.get(key)=" + map.get(key));
            }

            LogUtil.I("RequestNetWork_getXCList", "" + url);
            /**
             * 最后一个参数 如果数据模型是BaseListData 则传入集合里边的数据模型，如果数据类型为BaseData，则一定传空
             */
            GsonRequest<T> request = new GsonRequest<T>(Request.Method.GET, url, this, childClass);
            request.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            request.setShouldCache(false);
            HttpManager.getInstance().addToRequestQueue(request);
            return request;
        } catch (Exception e) {
            LogUtil.I(TAG, e.toString());
        }
        return null;
    }

    /**
     * 低级加密方式
     * @param loadType
     * @param context
     * @param url
     * @param map
     * @param childClass
     */
    public void PostXCList(int loadType, Context context, String url, Map<String, String> map, Class childClass) {
        PostXCList(loadType,context,url,null,map,childClass);
    }

    /**
     * 请求网络数据（带自动签名加密）
     * isNewUrl  true 是新接口
     *
     * @param loadType 0 没有loading  1 普通通用loading
     */
    public void PostXCList(int loadType, Context context, String url, String method, Map<String, String> map, Class childClass) {
        Map<String, String> paramMap = null;
        this.context = context;
        try {
            //对参数进行加密、签名
            if (method==null){
                paramMap = paramentRSA(map);
            }else {
                paramMap = paramentRSA(map, method);
            }
            for (String key : paramMap.keySet()) {
                LogUtil.I("RequestNetWork", "key=" + key + "--map.get(key)=" + map.get(key));
            }
            load(context, loadType);
            GsonRequest request = new GsonRequest(Request.Method.POST, url,paramMap,this, childClass);

            LogUtil.I("RequestNetWork_PostXCList", "" + url);

            request.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            // request.setShouldCache(true);
            request.setShouldCache(false);
            HttpManager.getInstance().addToRequestQueue(request);
        } catch (Exception e) {
            LogUtil.I(TAG, e.toString());
        }
    }

    @Override
    public void success(T object) {
        try {
            //判断10017（token失效情况）
            if (object instanceof BaseData) {
                BaseData baseData = (BaseData) object;
//                quitLogin(baseData.getRetCode(), baseData.getRetInfo());
            } else if (object instanceof BaseListDate) {
                BaseListDate baseData = (BaseListDate) object;
//                quitLogin(baseData.getRetCode(), baseData.getRetInfo());
            }
            success_(object);
        } finally {
            if (loadUtil != null) {
                loadUtil.highLoading();
            }
        }
    }

    @Override
    public void filed(Exception e) {
        LogUtil.I(TAG, e.toString());
        if (context != null) {
            String verifyIsFalse = AppConstant.verifyIsFalse;
            if (verifyIsFalse.equals(e.getMessage())) {
                ToastUtil.showShortToast(context == null ? ApplicationUtils.getAppContext() : context, verifyIsFalse);
            } else {
                ToastUtil.showShortToast(context == null ? ApplicationUtils.getAppContext() : context, AppConstant.verifyIsFalse);
            }
        }
        filed_(e);
        if (loadUtil != null) {
            loadUtil.highLoading();
        }
    }


    public void load(Context context, int type) {
        if (loadUtil == null) {
            loadUtil = new LoadUtil(context, type,false);
        }
        loadUtil.showLoading();
    }


    public abstract void success_(T object);

    public abstract void filed_(Exception e);

    //高级加密环节
    public static Map<String, String> paramentRSA(Map<String, String> map, String method) throws Exception {
        if (map == null) {
            map = new HashMap();
        }
        JSONObject jsonObject = new JSONObject();
        //把公共字段剔除，其余参数拼成Json串
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.equals(AppConstant.TOKEN_ID) || key.equals(AppConstant.APP_ID)
                    || key.equals(AppConstant.TIME_STAMP) || key.equals(AppConstant.NONCE)
                    || key.equals(AppConstant.METHOD) || key.equals(AppConstant.FORMAT)
                    || key.equals(AppConstant.CHARSET) || key.equals(AppConstant.VERSION)
                    || key.equals(AppConstant.COUNTRY) || key.equals(AppConstant.LANGUAGE)
                    || key.equals(AppConstant.BIZ_CONTENT) || key.equals(AppConstant.OUT_ACCESS)
                    || key.equals(AppConstant.SIGN) || key.equals(AppConstant.TIME_ZOME)) {
            } else {
                LogUtil.I("RequestNetWork", "key=" + entry.getKey() + "--map.get(key)=" + entry.getValue());
                jsonObject.put(entry.getKey(), entry.getValue());
            }
        }
        //时间戳

        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+08"));//设置
        day=df.parse(df.format(day));//将本地时间转换为转换时间为东八区
        String timestamp = df.format(day);
        String nonce = "";
        String sign = "";
        //开关控制是否加密
        LogUtil.D("RequestNetWork", "" + jsonObject.toString());
        if (IS_TO_RSA) {
            nonce = AppConstant.APP_ID + "=" + AppConstant.appId + "&appSecret=" + AppConstant.appSecret + "&timestamp=" + timestamp;
            //计算nonce参数
            nonce = new String(Base64.encodeBase64(WashCallApiSignSecurity.encryptMD5(nonce.getBytes())), UTF_8);

            byte[] encodeJsonData1 = WashCallApiSignSecurity.encryptByPublicKey(
                    new String(AppConstant.HaipublicKey.getBytes(), UTF_8), jsonObject.toString().getBytes(UTF_8));
            String bizcontent = new String(Base64.encodeBase64(encodeJsonData1), UTF_8);
            // 签名
            byte[] reqSignByte = WashCallApiSignSecurity.sign(
                    new String(AppConstant.privateKey.getBytes(), UTF_8),
                    encodeJsonData1);
            sign = new String(Base64.encodeBase64(reqSignByte), UTF_8);
            map.put(AppConstant.BIZ_CONTENT, bizcontent);
        } else {
            map.put(AppConstant.BIZ_CONTENT, jsonObject.toString());
        }


        map.put(AppConstant.SIGN, sign);
        map.put(AppConstant.NONCE, nonce);
//        map.put(AppConstant.TOKEN_ID, SharedPreferencesUtil.getPreference(MyApplication.getMyApplicationContext(),
//                SharedPreferencesUtil.USER_SP_FILE_NAME, SharedPreferencesUtil.USER_SP_TOKEN));
        map.put(AppConstant.TIME_STAMP, timestamp);
        map.put(AppConstant.APP_ID, AppConstant.appId);
        map.put(AppConstant.METHOD, method);
        map.put(AppConstant.FORMAT, "JSON");
        map.put(AppConstant.CHARSET, AppConstant.UTF_8);
        map.put(AppConstant.VERSION,AppConstant.API_VERSION);//接口版本，无需求不必变动
        map.put(AppConstant.COUNTRY, Locale.getDefault().getCountry());
        map.put(AppConstant.LANGUAGE, Locale.getDefault().getLanguage());
        map.put(AppConstant.TIME_ZOME, TimeUtils.getCurrentTimeZone());
//        map.put(AppConstant.OUT_ACCESS, "JSONP");

        return map;
    }

    /**
     * 低级加密
     * @param map
     * @return
     * @throws Exception
     */
    public static Map<String, String> paramentRSA(Map<String, String> map) throws Exception {
        if (map == null) {
            map = new HashMap();
        }
        String signOrl = (AppConstant.SIGN_HEAD + SignLexelUtil.createLinkString(SignLexelUtil.paraFilter(map))).toUpperCase();
        LogUtil.D("signOrl",signOrl);
        String sign = com.haier.hailicommontlib.mvp.model.utils.signutil.MD5Util.MD5Encode(signOrl, "UTF-8");
        map.put(AppConstant.SIGN, sign);
        map.put(AppConstant.SSID, DesUtil.SSID());
        map.put(AppConstant.APP_ID, AppConstant.appId);
        map.put(AppConstant.VERSION,ApplicationUtils.appUpdate());//接口版本，无需求不必变动
        return map;
    }


}
