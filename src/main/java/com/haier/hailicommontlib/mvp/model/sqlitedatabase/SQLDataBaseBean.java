package com.haier.hailicommontlib.mvp.model.sqlitedatabase;

import java.io.Serializable;

/**
 * @author： jjf
 * @date： 2019/7/5
 * @describe：支付订单数据库 （从下单到确认下单，异步返回结果时保存这个订单，确认支付结果后删除记录）
 */
public class SQLDataBaseBean implements Serializable{
    private String orderId;//海狸数据库订单Id
    private String creatTime;//时间
    private String thisOrderId;//当前支付平台的流水号或 订单Id
    private String payType;//支付类型

    public SQLDataBaseBean(String orderId, String creatTime, String thisOrderId, String payType) {
        this.orderId = orderId;
        this.creatTime = creatTime;
        this.thisOrderId = thisOrderId;
        this.payType = payType;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCreatTime() {
        return creatTime;
    }


    public String getThisOrderId() {
        return thisOrderId;
    }


    public String getPayType() {
        return payType;
    }


}
