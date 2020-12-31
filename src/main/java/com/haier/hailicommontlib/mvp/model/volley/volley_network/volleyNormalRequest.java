/**
 * Copyright 2013 Mani Selvaraj
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haier.hailicommontlib.mvp.model.volley.volley_network;

import android.view.View;

import com.google.gson.Gson;
import com.haier.hailicommontlib.mvp.model.AppConstant;
import com.haier.hailicommontlib.mvp.model.utils.LogUtil;
import com.haier.hailicommontlib.mvp.model.utils.SingleClickUtil;
import com.haier.hailicommontlib.mvp.model.utils.signutil.WashCallApiSignSecurity;
import com.haier.hailicommontlib.mvp.model.volley.AuthFailureError;
import com.haier.hailicommontlib.mvp.model.volley.NetworkResponse;
import com.haier.hailicommontlib.mvp.model.volley.ParseError;
import com.haier.hailicommontlib.mvp.model.volley.Request;
import com.haier.hailicommontlib.mvp.model.volley.Response;
import com.haier.hailicommontlib.mvp.model.volley.VolleyError;
import com.haier.hailicommontlib.mvp.model.volley.toolbox.HttpHeaderParser;
import com.haier.hailicommontlib.mvp_api.moudle.bean.BaseData;
import com.haier.hailicommontlib.mvp_api.moudle.bean.BaseListDate;
import com.haier.hailicommontlib.mvp_api.moudle.BaseSignCateData;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static com.haier.hailicommontlib.mvp.model.AppConstant.UTF_8;



/**
 * 基本volley请求的工具类
 */
public class volleyNormalRequest<T> extends Request<T> {
    private static final String TAG = "volleyNormalRequest";
    private Map<String, String> mMap;
    private BaseNetWorkInterface<T> mListener;
    private Map<String, String> mHeader = new HashMap<String, String>();
    private Class childClass;
    private int type;
    /**
     * 区分封装之后的基本类型
     */
    public static final int BEEN_TYPE_BASE_DATA = 1;
    public static final int BEEN_TYPE_BASE_LIST_DATA = 2;
    private Gson mGson;

    public volleyNormalRequest(String url, BaseNetWorkInterface<T> listener, Map<String, String> map, Class childClass, int type) {
        super(Request.Method.POST, url, listener);
        this.childClass = childClass;
        this.type = type;
        mListener = listener;
        mMap = map;
        mGson = new Gson();
        SingleClickUtil.setEnableToClick(false);

    }



    public volleyNormalRequest(String url, BaseNetWorkInterface errorListener, Map<String, String> map, View view) {
        super(Request.Method.POST, url, errorListener);
        mGson = new Gson();
        mListener = errorListener;
        mMap = map;
        SingleClickUtil.setEnableToClick(false);
    }

    public volleyNormalRequest(String url, BaseNetWorkInterface<T> listener, Map<String, String> map, Map<String, String> header) {
        super(Request.Method.POST, url, listener);
        mGson = new Gson();
        mListener = listener;
        mMap = map;
        mHeader = header;
        SingleClickUtil.setEnableToClick(false);
    }

    // mMap是已经按照前面的方式,设置了参数的实例
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mMap;
    }




    // 此处因为response返回值需要json数据,和JsonObjectRequest类一样即可
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            T parsedGSON = null;
            Type objectType = null;
            LogUtil.D(TAG, jsonString);
            String str1 = "";
            BaseSignCateData basebeen = mGson.fromJson(jsonString, BaseSignCateData.class);
            if (RequestNetWork.IS_TO_RSA&&null!=basebeen.getRetSign()&&!"null".equals(basebeen.getRetSign())&&!"".equals(basebeen.getRetSign())) {
                str1 = basebeen.getRetData();
                boolean boo = WashCallApiSignSecurity.verify(
                        AppConstant.HaipublicKey, Base64.decodeBase64(basebeen.getRetData().getBytes(UTF_8)),
                        Base64.decodeBase64(basebeen.getRetSign().getBytes(UTF_8)));

                if (boo) {
                    byte[] e = WashCallApiSignSecurity.decryptByPrivateKey(
                            AppConstant.privateKey.getBytes(), Base64.decodeBase64(basebeen.getRetData().getBytes(UTF_8)));
                    String retdata = new String(e, AppConstant.UTF_8);
                    LogUtil.I("GsonRequest",retdata);
                    String rsaRes=   jsonString.replace(str1,retdata);
                    if (childClass != null) {
                        if (type == BEEN_TYPE_BASE_DATA) {
                            JSONObject jsonObject=new JSONObject(retdata);
                            JSONObject jsons =  new JSONObject(jsonString);
                            jsons.put("retData",jsonObject);
                            objectType = type(BaseData.class, childClass);
                            parsedGSON = mGson.fromJson(jsons.toString(), objectType);
                        } else if (type == BEEN_TYPE_BASE_LIST_DATA) {
                            JSONArray jsonArray=new JSONArray(retdata);
                            LogUtil.I(TAG,""+jsonArray);
                            JSONObject jsons =  new JSONObject(jsonString);
                            jsons.put("retData",jsonArray);
                            objectType = type(BaseListDate.class, childClass);
                            parsedGSON = mGson.fromJson(jsons.toString(), objectType);
                        }
                    } else {
                         parsedGSON = mGson.fromJson(rsaRes, (Class<T>) BaseData.class);
                    }
                    return Response.success(parsedGSON, HttpHeaderParser.parseCacheHeaders(response));
                } else {
                    return Response.error(new ParseError(new Exception(AppConstant.verifyIsFalse)));
                }
            } else {
                if (childClass != null) {
                    if (type == BEEN_TYPE_BASE_DATA) {
                        objectType = type(BaseData.class, childClass);
                    } else if (type == BEEN_TYPE_BASE_LIST_DATA) {
                        objectType = type(BaseListDate.class, childClass);
                    }
                    parsedGSON = mGson.fromJson(jsonString, objectType);
                } else {
                    parsedGSON = mGson.fromJson(jsonString, (Class<T>) BaseData.class);
                }
                return Response.success(parsedGSON,
                        HttpHeaderParser.parseCacheHeaders(response));
            }
        } catch (UnsupportedEncodingException e) {
            LogUtil.E("volleyNormalRequest",""+e.toString());
            return Response.error(new ParseError(e));
        } catch (Exception e) {
            LogUtil.E("volleyNormalRequest","=="+e.toString());
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        SingleClickUtil.setEnableToClick(true);
        if (mListener != null) {
            LogUtil.D("volleyNormalRequest", response.toString());

            mListener.success(response);
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeader;
    }

    @Override
    public void deliverError(VolleyError error) {
        SingleClickUtil.setEnableToClick(true);

        super.deliverError(error);
    }


    //获取范型的type
    private ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
}
