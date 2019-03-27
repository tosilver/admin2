package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;

import java.math.BigDecimal;

/****
 * 易付宝批付下发记录
 * @author Tosdom
 */
public class Xiafa extends BaseEntity {

    private String tradeNo;             //下发单号
    private String receiverCardNo;     //收款方卡号
    private String receiverName;       //收款人姓名
    private String bankName;           //开户行名称
    private String bankCode;           //开户行编号
    private BigDecimal amount;         //付款金额
    private Long merchantId;           //商户ID
    private Long batch;              //批次
    private Integer request;            //是否已请求


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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }


    public Long getBatch() {
        return batch;
    }

    public void setBatch(Long batch) {
        this.batch = batch;
    }

    public Integer getRequest() {
        return request;
    }

    public void setRequest(Integer request) {
        this.request = request;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}
