package com.haier.hailicommontlib.mvp.model;



/**
 * @author： 宋庆伟
 * @date： 2020/9/18
 * @describe：
 */
public class AppConstant {
    //微信APPid
    public static final String WECHAT_APP_ID = "wxd6a81d3ac87fa1c3";

    public static final String UTF_8 = "UTF-8";

    /**
     * 请求成功
     */
    public static final String SUCCESS_NET_WORK = "00000";
    /**
     * token失效
     */
    public static final String ERROR_NET_WORK_TOKEN_ID = "10017";
    /**
     * tokenId为空
     */
    public static final String ERROR_NET_WORK_TOKEN_TOW_ID = "10001";

    /**
     * 接口单次返回list数据条目数量
     */
    public static final int LIST_RSULT_DATA_SIZE = 10;

    //海尔洗衣加密公钥预留
    public static final String HaipublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwsVMbXoA9Lpd8qFdyoeAXyTCX\n" +
            "8XmB+UMdvfseuJnMMtQBw457UGHG/TrQvNGgJfGFpPcav4vTGVHELaVl6h5M7D7C\n" +
            "Wvu/tFTnXu3ZErp/gVNqKwfEObAocD22U31NOATe9yvUpG4rE22mdw0LWgXe/Kex\n" +
            "AF3zGbYKKeihj9dVqwIDAQAB";
    //应用签名私钥预留
    public static final String privateKey = "MIICXAIBAAKBgQCwsVMbXoA9Lpd8qFdyoeAXyTCX8XmB+UMdvfseuJnMMtQBw457\n" +
            "UGHG/TrQvNGgJfGFpPcav4vTGVHELaVl6h5M7D7CWvu/tFTnXu3ZErp/gVNqKwfE\n" +
            "ObAocD22U31NOATe9yvUpG4rE22mdw0LWgXe/KexAF3zGbYKKeihj9dVqwIDAQAB\n" +
            "AoGBAJneIcAwwD4HR9Y/hr+Fpe8wvcdjemusFD1fRsLPAOeoz6SllnteWXd+5+wE\n" +
            "sgvjXvsB2EoLygjQALt1AJbKlQz25Iq3sk/P/exLtcs7STwb4J6TQ2pe/OwXOfOX\n" +
            "2ws7ondZ3uhj3dwke4Q3Rp9+SIdhTbssRz5zMCUkyoBSyAHJAkEA3bRk45ym9CcJ\n" +
            "wPD0woz6yxNt4tVEU2Nr0PZ+4gob/taVCgm1kv67W2wTwBk5tRLLSBkK8vMyfmWE\n" +
            "zE2RGnKRbwJBAMwGb2hkzyGJZNai+ROtiW0Cdn+RbAEE91j3MGbNfOJUyNwE98+Q\n" +
            "AtZYFH1aYAqaXtYHUJHLnK3kMhujEhXpKYUCQFqzvsLQCA3DGt5wtfISM1h5rm8q\n" +
            "yTVEa4umDOr1L9bmezsMFdj5ljUk1j+1EkLeWYQ5mp0imfnUFqxu33pufMkCQE3v\n" +
            "62D6mXFbNc0UJofQ091zgmbK23IsPnyauL+coacn8i6yKyP8BElzDiiwfF2wiKfD\n" +
            "1ZBMMobu1AFgssjx760CQESNGXcDNEzCtSbNmoCZIfMW+yT+AVCNABhO9qGxCR1u\n" +
            "K4MBkl7SjTMYsWohr//+Clea4Lfs65pf6Xf7fwZuaFw=";

    public static final String appId = "hl7c87b100fc2143a";
    public static final String appSecret = "759fcd66ad674c80a250753bd7fd5fcb";

    /**
     * 高级加密接口公共参数
     */
    public static final String TOKEN_ID = "tokenId";//请求令牌、安全令牌
    public static final String APP_ID = "appId";
    public static final String TIME_STAMP = "timestamp";//请求时间 yyyyMMddHHmmss
    public static final String NONCE = "nonce";//请求授权验证信息
    public static final String METHOD = "method";//接口名称
    public static final String FORMAT = "format";//仅支持JSON
    public static final String CHARSET = "charset";//请求使用的编码格式（目前仅支持utf-8）
    public static final String VERSION = "version";//调用的接口版本
    public static final String COUNTRY = "country";//请求发起的国家
    public static final String LANGUAGE = "language";//请求使用的语言
    public static final String BIZ_CONTENT = "bizcontent";//请求参数的集合，除公共参数外，所有参数都必须经加密放在这个参数中传递
    public static final String OUT_ACCESS = "outAccess";//跨域授权，跨域访问接口时传入此值，接口返回跨域格式 JSONP为传入值outAccess
    public static final String TIME_ZOME = "timeZone";//请求时区
    public static final String SIGN = "sign";//签名
    public static final String API_VERSION = "1.0.0";
    /**
     * 低级加密公共参数
     */
    public static final String CODE = "hlF#*(x)";
    public static final String KEY = "saywash#";
    public static final String SIGN_HEAD = "SING=HLYF&";
    public static final String SSID = "ssid";
    /**
     * 验签失败异常
     */
    public static final String verifyIsFalse = "验签失败，非法请求！";



}
