package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;

import java.math.BigDecimal;

/****
 * 易付宝批付到卡 订单详情
 * LWH
 */
public class DetailDataCard extends BaseEntity {
    private String serialNo;           //订单号
    private String orderName;          //订单名
    private String receiverCardNo;     //收款方卡号
    private String receiverName;       //收款人姓名
    private String receiverType;       //收款方类型（PERSON 个人 CORP 企业）
    private String bankName;           //开户行名称
    private String bankCode;           //开户行编号
    private String bankProvince;       //开户行省
    private String bankCity;           //开户行市
    private String payeeBankLinesNo;   //联行号
    private BigDecimal amount;               //付款金额
    private String batchNo;              //所属批量订单号
    private String company;              //商家名
    private Long merchantId;               //商户ID
    private BigDecimal merchantPayCost;  //商户代付成本
    private Long xiafaId;                  //下发记录id

    @Override
    public String toString() {
        return "DetailDataCard{" +
                "serialNo='" + serialNo + '\'' +
                ", orderName='" + orderName + '\'' +
                ", receiverCardNo='" + receiverCardNo + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", receiverType='" + receiverType + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", bankProvince='" + bankProvince + '\'' +
                ", bankCity='" + bankCity + '\'' +
                ", payeeBankLinesNo='" + payeeBankLinesNo + '\'' +
                ", amount=" + amount +
                ", batchNo='" + batchNo + '\'' +
                ", company='" + company + '\'' +
                ", merchantId=" + merchantId +
                ", merchantPayCost=" + merchantPayCost +
                ", xiafaId=" + xiafaId +
                ", id='" + id + '\'' +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }

    public Long getXiafaId() {
        return xiafaId;
    }

    public void setXiafaId(Long xiafaId) {
        this.xiafaId = xiafaId;
    }

    public BigDecimal getMerchantPayCost() {
        return merchantPayCost;
    }

    public void setMerchantPayCost(BigDecimal merchantPayCost) {
        this.merchantPayCost = merchantPayCost;
    }

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

    public String getReceiverCardNo() {
        return receiverCardNo;
    }

    public void setReceiverCardNo(String receiverCardNo) {
        this.receiverCardNo = receiverCardNo;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankProvince() {
        return bankProvince;
    }

    public void setBankProvince(String bankProvince) {
        this.bankProvince = bankProvince;
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    public String getPayeeBankLinesNo() {
        return payeeBankLinesNo;
    }

    public void setPayeeBankLinesNo(String payeeBankLinesNo) {
        this.payeeBankLinesNo = payeeBankLinesNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
