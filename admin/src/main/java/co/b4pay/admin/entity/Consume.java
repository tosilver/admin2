package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;


import java.math.BigDecimal;

/**
 * 消费记录
 *
 * @author YK
 * @version $Id: Consume.java, v 0.1 2018年4月21日 上午11:35:09 YK Exp $
 */
public class Consume extends BaseEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3050118815232663086L;

    private Merchant merchant;                              // 商户信息
    private Channel channel;                                // 通道信息
    private MallAddress mallAddress;                        // 地址信息
    private Long time;                                  // 耗时（毫秒）
    private BigDecimal totalAmount;                            //总交易金额（元）
    private BigDecimal requestAmount;                            //实际请求金额（元）
    private String header;                                // 请求头
    private String request;                               // 请求摘要
    private String response;                              // 响应结果

    private String merchantOrderNo;        //商户订单号
    private String payOrderNo;             //支付订单号
    private BigDecimal costRate;               //成本费率
    private BigDecimal payCost;                //代付成本
    private BigDecimal serviceCharge;          //手续费
    private BigDecimal accountAmount;          //到账金额
    private BigDecimal fzAmount;                //支付宝分账金额
    private int tradeState;             //支付状态：0 未支付 1支付成功  -1 支付失败 -2 关闭
    private Integer fzStatus;             //分账状态：0 未分账 1分账成功  2分账失败
    private Long transinId;                   //支付宝分账用户id
    private String contacts;
    private String name;
    private String paymentTime;

    private Router router;

    @Override
    public String toString() {
        return "Consume{" +
                "merchant=" + merchant +
                ", channel=" + channel +
                ", mallAddress=" + mallAddress +
                ", time=" + time +
                ", totalAmount=" + totalAmount +
                ", requestAmount=" + requestAmount +
                ", header='" + header + '\'' +
                ", request='" + request + '\'' +
                ", response='" + response + '\'' +
                ", merchantOrderNo='" + merchantOrderNo + '\'' +
                ", payOrderNo='" + payOrderNo + '\'' +
                ", costRate=" + costRate +
                ", payCost=" + payCost +
                ", serviceCharge=" + serviceCharge +
                ", accountAmount=" + accountAmount +
                ", fzAmount=" + fzAmount +
                ", tradeState=" + tradeState +
                ", fzStatus=" + fzStatus +
                ", transinId=" + transinId +
                ", contacts='" + contacts + '\'' +
                ", name='" + name + '\'' +
                ", paymentTime='" + paymentTime + '\'' +
                ", router=" + router +
                '}';
    }

    public BigDecimal getFzAmount() {
        return fzAmount;
    }

    public void setFzAmount(BigDecimal fzAmount) {
        this.fzAmount = fzAmount;
    }

    public Long getTransinId() {
        return transinId;
    }

    public void setTransinId(Long transinId) {
        this.transinId = transinId;
    }

    public Integer getFzStatus() {
        return fzStatus;
    }

    public void setFzStatus(Integer fzStatus) {
        this.fzStatus = fzStatus;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public BigDecimal getCostRate() {
        return costRate;
    }

    public void setCostRate(BigDecimal costRate) {
        this.costRate = costRate;
    }

    public BigDecimal getPayCost() {
        return payCost;
    }

    public void setPayCost(BigDecimal payCost) {
        this.payCost = payCost;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public BigDecimal getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(BigDecimal accountAmount) {
        this.accountAmount = accountAmount;
    }

    public int getTradeState() {
        return tradeState;
    }

    public void setTradeState(int tradeState) {
        this.tradeState = tradeState;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public MallAddress getMallAddress() {
        return mallAddress;
    }

    public void setMallAddress(MallAddress mallAddress) {
        this.mallAddress = mallAddress;
    }

    public BigDecimal getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(BigDecimal requestAmount) {
        this.requestAmount = requestAmount;
    }
}
