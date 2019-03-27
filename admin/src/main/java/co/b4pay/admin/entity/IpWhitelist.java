package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;


/**
 * IP白名单
 *
 * @author YK
 * @version $Id: IpWhitelist.java, v 0.1 2018年4月21日 下午16:05:09 YK Exp $
 */
public class IpWhitelist extends BaseEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3050115515232693586L;

    private Merchant merchant;                             // 商户
    private String ip;                                   // IP
    private String desc;                                 // 描述

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
