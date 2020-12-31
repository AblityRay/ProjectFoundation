package com.haier.hailicommontlib.mvp.model.volley.util;

import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * @author： jjf
 * @date： 2020/9/25
 * @describe：
 */
public class StringUtil {


    /**
     * @param json
     * @return 1 json为数组类型   2json 为Object类型   3String 类型
     */
    public static int jsonIsArray(String json) {

        Object listArray = null;//取出JSONArray中的数据
        try {
            listArray = new JSONTokener(json).nextValue();

            if (listArray instanceof JSONArray) {//判断是JSONArray还是JSONObject
                return 1;

            } else if (listArray instanceof JSONObject) {
                JSONObject jsonObject3 = (JSONObject) listArray;

                return 2;
            }

        } catch (JSONException | JsonSyntaxException e) {
            e.printStackTrace();
            return 3;
        }
        return 3;
    }
}
