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
import com.haier.hailicommontlib.mvp.model.volley.VolleyLog;
import com.haier.hailicommontlib.mvp.model.volley.toolbox.HttpHeaderParser;
import com.haier.hailicommontlib.mvp.model.volley.util.StringUtil;
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

public class GsonRequest<T> extends Request<T> {

    /**
     * Charset for request.
     */
    private static final String PROTOCOL_CHARSET = "utf-8";
    private final String TAG = "GsonRequest";
    /**
     * Content type for request.
     */
    private static final String PROTOCOL_CONTENT_TYPE = String.format(
            "application/json; charset=%s", PROTOCOL_CHARSET);

    private final BaseNetWorkInterface<T> mListener;

    private final String mRequestBody;
    private Gson mGson;
    private Class childClass;
    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> mMap;

    public GsonRequest(int method, String url, Map<String, String> map, BaseNetWorkInterface<T> listener, Class childClass) {
        super(method, url, listener);
        mGson = new Gson();
        this.childClass = childClass;
        mListener = listener;
        mRequestBody = "";
        mMap = map;
        SingleClickUtil.setEnableToClick(false);
    }

    /**
     * 区分范型类别
     *
     * @param method
     * @param url
     * @param listener
     * @param childClass
     */
    public GsonRequest(int method, String url, BaseNetWorkInterface<T> listener, Class childClass) {
        super(method, url, listener);
        mGson = new Gson();
        this.childClass = childClass;
        mListener = listener;
        mRequestBody = "";
        this.headers = headers;
        SingleClickUtil.setEnableToClick(false);
    }


    @Override
    protected void deliverResponse(T response) {
        SingleClickUtil.setEnableToClick(true);
        if (mListener != null) {
            LogUtil.I(TAG, "RequestNetWork_deliverResponse" + response.toString());
            mListener.success(response);
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        return headers;
    }

    // mMap是已经按照前面的方式,设置了参数的实例
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mMap;
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String jsonString = null;
        try {
            jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            T parsedGSON = null;
            Type objectType = null;
            LogUtil.D(TAG, "1111----" + jsonString);
            String str1 = "";
            BaseSignCateData basebeen = mGson.fromJson(jsonString, type(BaseSignCateData.class));
            if (RequestNetWork.IS_TO_RSA && null != basebeen.getRetSign() && !"".equals(basebeen.getRetSign()) && !"null".equals(basebeen.getRetSign())) {
                boolean boo = WashCallApiSignSecurity.verify(AppConstant.HaipublicKey, Base64.decodeBase64(basebeen.getRetData().getBytes(UTF_8)),
                        Base64.decodeBase64(basebeen.getRetSign().getBytes(UTF_8)));
                //验签通过
                LogUtil.D(TAG, "boo===" + boo);
                if (boo) {
                    str1 = basebeen.getRetData();
                    byte[] e = WashCallApiSignSecurity.decryptByPrivateKey(AppConstant.privateKey.getBytes(), Base64.decodeBase64(basebeen.getRetData().getBytes(UTF_8)));
                    String retdata = new String(e, AppConstant.UTF_8);
                    String rsaRes = jsonString.replace(str1, retdata);
                    LogUtil.I(TAG, "" + rsaRes);
                    if (childClass != null) {

                        if (2 == StringUtil.jsonIsArray(retdata)) {
                            JSONObject jsonObject = new JSONObject(retdata);
                            JSONObject jsons = new JSONObject(jsonString);
                            jsons.put("retData", jsonObject);
                            objectType = type(BaseData.class, childClass);
                            LogUtil.D("GsonRequest", "" + jsons.toString());
                            parsedGSON = mGson.fromJson(jsons.toString(), objectType);
                        } else if (1 == StringUtil.jsonIsArray(retdata)) {
                            JSONArray jsonArray = new JSONArray(retdata);
                            LogUtil.I(TAG, "" + jsonArray);
                            JSONObject jsons = new JSONObject(jsonString);
                            jsons.put("retData", jsonArray);
                            objectType = type(BaseListDate.class, childClass);
                            parsedGSON = mGson.fromJson(jsons.toString(), objectType);
                        } else {
                            parsedGSON = mGson.fromJson(rsaRes, (Class<T>) BaseData.class);
                        }
                    } else {
                        parsedGSON = mGson.fromJson(rsaRes, (Class<T>) BaseData.class);
                    }
                } else {
                    return Response.error(new ParseError(new Exception(AppConstant.verifyIsFalse)));
                }
            } else {
                if (childClass != null) {
                    if (2 == StringUtil.jsonIsArray(basebeen.getRetData())) {
                        objectType = type(BaseData.class, childClass);
                        parsedGSON = mGson.fromJson(jsonString.toString(), objectType);
                    } else if (2 == StringUtil.jsonIsArray(basebeen.getRetData())) {
                        objectType = type(BaseListDate.class, childClass);
                        parsedGSON = mGson.fromJson(jsonString.toString(), objectType);
                    }
                } else {
                    parsedGSON = mGson.fromJson(jsonString, (Class<T>) BaseData.class);
                }
            }
            return Response.success(parsedGSON, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception je) {
            LogUtil.I(TAG, "  GsonRequest " + je.toString());
            return Response.error(new ParseError(je));
        }
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

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    @Override
    public byte[] getBody() {
        try {
            return mRequestBody == null ? null : mRequestBody
                    .getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog
                    .wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            mRequestBody, PROTOCOL_CHARSET);
            return null;
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        SingleClickUtil.setEnableToClick(true);
        super.deliverError(error);
    }

}
