package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Tosdom
 */
public class SysShangyou extends BaseEntity {
    private String realname;
    private String username;
    private String password;
    private String salt;
    private String channelIds;//拥有的渠道列表

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getChannelIds() {
        return channelIds;
    }

    public void setChannelIds(String channelIds) {
        this.channelIds = channelIds;
    }

    @Override
    public String toString() {
        return "SysShangyou{" +
                "realname='" + realname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", channelIds='" + channelIds + '\'' +
                '}';
    }

    public String getCredentialsSalt() {
        return username + salt;
    }
}
