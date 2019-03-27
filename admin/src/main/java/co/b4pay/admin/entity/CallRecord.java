package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;

import java.math.BigDecimal;

public class CallRecord extends BaseEntity {
    private String merchantId;   //商户ID
    private String customerId;   //客户ID
    private String channelId;    //交易通道ID
    private BigDecimal amount;        //交易金额
    private String cardNo;         //交易银行卡号
    private String downOrderId;    //下游订单号
    private String upOrderId;      //上游订单号
    private String callType;      //请求类型 1主动请求 2同步回调 3异步通知  status-1待处理 0 失败 1 成功 2 处理中
    private String content;      //请求/返回内容

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getDownOrderId() {
        return downOrderId;
    }

    public void setDownOrderId(String downOrderId) {
        this.downOrderId = downOrderId;
    }

    public String getUpOrderId() {
        return upOrderId;
    }

    public void setUpOrderId(String upOrderId) {
        this.upOrderId = upOrderId;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
