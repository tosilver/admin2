package co.b4pay.admin.entity;


import java.math.BigDecimal;

import co.b4pay.admin.entity.base.BaseEntity;


/**
 * 商户费率
 * Created by john on 2018/6/7.
 */
public class MerchantRate extends BaseEntity {
    private static final long serialVersionUID = -4560729645400637821L;

    private Merchant merchant;          //商户信息
    private Router router;          //路由(产品)信息
    private String costRate;            //结算费率
    private BigDecimal payCost;             //代付成本
    private BigDecimal withdrawCost;        //提现成本

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public String getCostRate() {
        return costRate;
    }

    public void setCostRate(String costRate) {
        this.costRate = costRate;
    }

    public BigDecimal getPayCost() {
        return payCost;
    }

    public void setPayCost(BigDecimal payCost) {
        this.payCost = payCost;
    }

    public BigDecimal getWithdrawCost() {
        return withdrawCost;
    }

    public void setWithdrawCost(BigDecimal withdrawCost) {
        this.withdrawCost = withdrawCost;
    }
}
