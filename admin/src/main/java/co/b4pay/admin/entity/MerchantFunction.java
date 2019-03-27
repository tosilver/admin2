package co.b4pay.admin.entity;


import co.b4pay.admin.entity.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 商户接口
 *
 * @author YK
 * @version $Id: MerchantFunction.java, v 0.1 2018年4月21日 下午18:29:09 YK Exp $
 */
public class MerchantFunction extends BaseEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3050115515332663086L;

    private Merchant merchant;                            // 商户信息
    private Router router;                             // 接口信息
    @Deprecated
    private Channel channel;                             // 通道信息，已废弃，商户信息直接引用通道信息
    private BigDecimal fee;     // 单次调用费用（元）

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

    @Deprecated
    public Channel getChannel() {
        return channel;
    }

    @Deprecated
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}
