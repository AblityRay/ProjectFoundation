package com.haier.hailicommontlib.mvp.model.bean.pay;

import com.haier.hailicommontlib.mvp.model.interfaces.IPayResultBean;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;

/**
 * @author： jjf
 * @date： 2020/9/24
 * @describe：
 */
public class WxPayResultBean implements IPayResultBean {
    private BaseResp resultData;

    public WxPayResultBean(BaseResp resultData) {
        this.resultData = resultData;
    }

    public BaseResp getResultData() {
        return resultData;
    }

    public void setResultData(BaseResp resultData) {
        this.resultData = resultData;
    }
}
