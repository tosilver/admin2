package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 渠道信息
 *
 * @author YK
 * @version $Id: Channel.java, v 0.1 2018年4月21日 下午22:09:09 YK Exp $
 */
public class Channel extends BaseEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3050115515392663063L;

    private Integer product;                                      //通道标志
    private String name;                                         // 通道名
    private String testPid;                                      // 测试环境商户PID
    private String testPrivateKey;                              // 测试环境私钥
    private String testPublicKey;                               // 测试环境公钥
    private String testAppid;                                   //测试环境APPID
    private String prodPid;                                      // 生产环境商户PID
    private String prodPrivateKey;                              // 生产环境私钥
    private String prodPublicKey;                               // 生产环境公钥
    private String prodAppid;                                    //生产环境APPID
    private BigDecimal amountInit;                                   //初始额度（单位：元）
    private BigDecimal amountLimit;                                  //剩余额度（单位：元），24小时内重置
    private BigDecimal payCost;                                      //上游代付成本
    private BigDecimal costRate;                                     //上游成本费率
    private BigDecimal unitPrice;                          //单笔交易最低限额
    private BigDecimal minPrice;                           //单笔交易最低限额
    private Router router;                                   //路由
    private GoodsType goodsType;                           //商品类型
    private String dstDescribe;                                    //渠道描述
    private String ip4;                                            //ip4
    private BigDecimal amountMin;                                  //最低额度
    private BigDecimal rate;                                       //单笔交易成单速率
    private Float fzPercentage;                                    //支付宝分账百分比

    public Float getFzPercentage() {
        return fzPercentage;
    }

    public void setFzPercentage(Float fzPercentage) {
        this.fzPercentage = fzPercentage;
    }

    public String getDstDescribe() {
        return dstDescribe;
    }

    public void setDstDescribe(String dstDescribe) {
        this.dstDescribe = dstDescribe;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getProduct() {
        return product;
    }

    public void setProduct(Integer product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTestPid() {
        return testPid;
    }

    public void setTestPid(String testPid) {
        this.testPid = testPid;
    }

    public String getTestPrivateKey() {
        return testPrivateKey;
    }

    public void setTestPrivateKey(String testPrivateKey) {
        this.testPrivateKey = testPrivateKey;
    }

    public String getTestPublicKey() {
        return testPublicKey;
    }

    public void setTestPublicKey(String testPublicKey) {
        this.testPublicKey = testPublicKey;
    }

    public String getTestAppid() {
        return testAppid;
    }

    public void setTestAppid(String testAppid) {
        this.testAppid = testAppid;
    }

    public String getProdPid() {
        return prodPid;
    }

    public void setProdPid(String prodPid) {
        this.prodPid = prodPid;
    }

    public String getProdPrivateKey() {
        return prodPrivateKey;
    }

    public void setProdPrivateKey(String prodPrivateKey) {
        this.prodPrivateKey = prodPrivateKey;
    }

    public String getProdPublicKey() {
        return prodPublicKey;
    }

    public void setProdPublicKey(String prodPublicKey) {
        this.prodPublicKey = prodPublicKey;
    }

    public String getProdAppid() {
        return prodAppid;
    }

    public void setProdAppid(String prodAppid) {
        this.prodAppid = prodAppid;
    }

    public BigDecimal getAmountInit() {
        return amountInit;
    }

    public void setAmountInit(BigDecimal amountInit) {
        this.amountInit = amountInit;
    }

    public BigDecimal getAmountLimit() {
        return amountLimit;
    }

    public void setAmountLimit(BigDecimal amountLimit) {
        this.amountLimit = amountLimit;
    }

    public BigDecimal getPayCost() {
        return payCost;
    }

    public void setPayCost(BigDecimal payCost) {
        this.payCost = payCost;
    }

    public BigDecimal getCostRate() {
        return costRate;
    }

    public void setCostRate(BigDecimal costRate) {
        this.costRate = costRate;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    public String getIp4() {
        return ip4;
    }

    public void setIp4(String ip4) {
        this.ip4 = ip4;
    }

    public BigDecimal getAmountMin() {
        return amountMin;
    }

    public void setAmountMin(BigDecimal amountMin) {
        this.amountMin = amountMin;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
