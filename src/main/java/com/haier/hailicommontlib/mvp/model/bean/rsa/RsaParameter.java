package com.haier.hailicommontlib.mvp.model.bean.rsa;

/**
 * @author： jjf
 * @date： 2020/11/25
 * @describe：Rsa加密之后 数据
 */
public class RsaParameter {
    private String parameter;
    private String sign;

    public RsaParameter(String parameter, String sign) {
        this.parameter = parameter;
        this.sign = sign;
    }

    public String getParameter() {
        return parameter;
    }

    public String getSign() {
        return sign;
    }
}
