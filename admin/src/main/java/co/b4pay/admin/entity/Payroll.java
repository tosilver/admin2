package co.b4pay.admin.entity;

import java.math.BigDecimal;

import co.b4pay.admin.entity.base.BaseEntity;


/***
 * 银生宝代付信息
 */
public class Payroll extends BaseEntity {

    private static final long serialVersionUID = 1664292656938618296L;
    private String downOrderId;       //下游订单号
    private String upOrderId;        //下游订单号
    private Long merchantId;      //付款商户Id
    private String company;        //商户名
    private String content;        //请求返回内容
    private String name;            //收款人姓名
    private String cardNo;          //收款人银行卡号
    private String purpose;         //付款目的
    private BigDecimal amount;          //付款金额
    private String idCardNo;        //收款人身份证
    private String summary;         //付款摘要
    private BigDecimal merchantPayCost;  //商户代付成本
    private BigDecimal channelPayCost;  //渠道代付成本
    private String notifyUrl;       //响应地址
    private int businessType;    //业务种类

    private String mac;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * @return the downOrderId
     */
    public String getDownOrderId() {
        return downOrderId;
    }

    /**
     * @param downOrderId the downOrderId to set
     */
    public void setDownOrderId(String downOrderId) {
        this.downOrderId = downOrderId;
    }

    /**
     * @return the upOrderId
     */
    public String getUpOrderId() {
        return upOrderId;
    }

    /**
     * @param upOrderId the upOrderId to set
     */
    public void setUpOrderId(String upOrderId) {
        this.upOrderId = upOrderId;
    }

    /**
     * @return the mac
     */
    public String getMac() {
        return mac;
    }

    /**
     * @param mac the mac to set
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     *
     */


    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }


    public BigDecimal getMerchantPayCost() {
        return merchantPayCost;
    }

    public void setMerchantPayCost(BigDecimal merchantPayCost) {
        this.merchantPayCost = merchantPayCost;
    }

    public BigDecimal getChannelPayCost() {
        return channelPayCost;
    }

    public void setChannelPayCost(BigDecimal channelPayCost) {
        this.channelPayCost = channelPayCost;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
