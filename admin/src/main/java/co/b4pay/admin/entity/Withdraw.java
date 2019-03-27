package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;

import java.math.BigDecimal;

/****
 * 提现记录
 * @author Tosdom
 */
public class Withdraw extends BaseEntity {


    private String merchantId;            //商户ID
    private String tradeNo;             //提现单号
    private String merchantCompany;     //商户名称
    private String cardNo;               //银行卡号
    private String customerName;       //户主姓名
    private String bankName;           //开户行名称
    private BigDecimal amount;         //付款金额
    private String appTime;            //审批时间


    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getMerchantCompany() {
        return merchantCompany;
    }

    public void setMerchantCompany(String merchantCompany) {
        this.merchantCompany = merchantCompany;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAppTime() {
        return appTime;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }
}
