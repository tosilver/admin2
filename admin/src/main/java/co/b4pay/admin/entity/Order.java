package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单记录
 */
public class Order extends BaseEntity {

    private String orderId;                 //订单号
    private BigDecimal orderMoney;          //交易金额
    private String account;                 //支付宝收款账号
    private String memo;
    private String qrcode;                  //二维码地址
    private Integer payType;                //支付类型:支付宝-1,微信-2;

    private Date payTime;                   //支付时间
    private Integer payStatus;              //支付状态:未支付-0,已支付-1;
    private BigDecimal needMoney;           //应付金额
    private BigDecimal payedMoney;          //已付金额
    private String notifyUrl;               //回调地址
    private String text1;                   //备用字段

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderMoney=" + orderMoney +
                ", account='" + account + '\'' +
                ", memo='" + memo + '\'' +
                ", qrcode='" + qrcode + '\'' +
                ", payType=" + payType +
                ", payTime=" + payTime +
                ", payStatus=" + payStatus +
                ", needMoney=" + needMoney +
                ", payedMoney=" + payedMoney +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", text1='" + text1 + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public BigDecimal getNeedMoney() {
        return needMoney;
    }

    public void setNeedMoney(BigDecimal needMoney) {
        this.needMoney = needMoney;
    }

    public BigDecimal getPayedMoney() {
        return payedMoney;
    }

    public void setPayedMoney(BigDecimal payedMoney) {
        this.payedMoney = payedMoney;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }
}
