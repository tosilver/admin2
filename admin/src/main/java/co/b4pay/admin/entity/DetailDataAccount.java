package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;

import java.math.BigDecimal;

/*****
 * 易付宝批付到易付宝账户 订单详情
 */
public class DetailDataAccount extends BaseEntity {

    private String serialNo;             //订单流水号
    private String orderName;            //订单名
    private String receiverLoginName;    //易付宝登入名{仅收款方为个人时传递}
    private String receiverNo;           //收款方商户号{仅收款方为商户时传递}
    private String receiverName;         //收款方姓名{仅在收款方为个人时传递。收款方为商户时可空，最大128字节}
    private BigDecimal amount;               //金额
    private String receiverType;         //收款方类型(PERSON：个人；CORP：企业)
    private String batchNo;      //所属批量订单号
    private String company;
    private Long merchantId;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getReceiverLoginName() {
        return receiverLoginName;
    }

    public void setReceiverLoginName(String receiverLoginName) {
        this.receiverLoginName = receiverLoginName;
    }

    public String getReceiverNo() {
        return receiverNo;
    }

    public void setReceiverNo(String receiverNo) {
        this.receiverNo = receiverNo;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }
}
