package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;


import java.math.BigDecimal;

/**
 * 充值记录
 *
 * @author YK
 * @version $Id: Recharge.java, v 0.1 2018年4月20日 下午23:55:09 YK Exp $
 */
public class Recharge extends BaseEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3050115515232663085L;

    private Merchant merchant;                            // 商户信息
    private BigDecimal money;                               // 充值金额

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
