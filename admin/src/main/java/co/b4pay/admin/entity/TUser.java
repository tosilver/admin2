package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;

/**
 * @author Tosdom
 */
public class TUser extends BaseEntity {
    private String account;
    private String userid;
    private Integer payType;
    private Integer status;

    @Override
    public String toString() {
        return "TUser{" +
                "account='" + account + '\'' +
                ", userid='" + userid + '\'' +
                ", payType=" + payType +
                ", status=" + status +
                '}';
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public void setStatus(Integer status) {
        this.status = status;
    }
}
