package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;

import java.math.BigDecimal;

/****
 * 易付宝批付批次信息
 */
public class YfbPayroll extends BaseEntity {
    private String merchantId;             //商户ID
    private String tradeNo;                //下发订单号
    private String company;               //商户名
    private String batchNo;                //批量订单号
    private String batchOrderName;         //批量订单名
    private Integer totalNum;              //批量总笔数
    private BigDecimal totalAmount;         //批量金额
    private String notifyUrl;              //通知地址
    private String routerId;               //渠道Id
    private String goodsType;           //商品类型编码
    private String content;             //易付宝给的回调内容

    @Override
    public String toString() {
        return "YfbPayroll{" +
                "merchantId='" + merchantId + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", company='" + company + '\'' +
                ", batchNo='" + batchNo + '\'' +
                ", batchOrderName='" + batchOrderName + '\'' +
                ", totalNum=" + totalNum +
                ", totalAmount=" + totalAmount +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", routerId='" + routerId + '\'' +
                ", goodsType='" + goodsType + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBatchOrderName() {
        return batchOrderName;
    }

    public void setBatchOrderName(String batchOrderName) {
        this.batchOrderName = batchOrderName;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getRouterId() {
        return routerId;
    }

    public void setRouterId(String routerId) {
        this.routerId = routerId;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }
}
