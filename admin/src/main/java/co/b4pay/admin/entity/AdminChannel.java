package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 管理员渠道关联类
 */
public class AdminChannel extends BaseEntity {

    private BigDecimal adminId;//管理员ID

    private String channelId;//渠道ID

    public AdminChannel() {
        super();
    }

    public AdminChannel(BigDecimal adminId, String channelId) {
        this.adminId = adminId;
        this.channelId = channelId;
    }

    public BigDecimal getAdminId() {
        return adminId;
    }

    public void setAdminId(BigDecimal adminId) {
        this.adminId = adminId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
