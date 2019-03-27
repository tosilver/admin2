package co.b4pay.admin.common.system.entity;

import co.b4pay.admin.entity.base.BaseEntity;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户表
 *
 * @author cc
 */
public class Admin extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String realname;                                    // 编号
    private Long organizationId;                             // 组织机构
    private String username;                                   // 用户名
    private String password;                                   // 密码
    private String salt;                                       // 加密密码的盐
    // private String roleIdsStr;
    private Boolean locked = Boolean.FALSE;
    private String name;                                       // 姓名或昵称
    private String email;                                      // 电子邮件
    private String phone;                                      // 电话
    private String mobile;                                     // 手机
    private String photo;                                      // 头像

    private List<String> roleIds = new ArrayList<String>(); // 拥有的角色列表

    private List<String> merchantIds = new ArrayList<String>(); //拥有的商户列表

    private String channelIds;//拥有的渠道列表

    public Admin() {
        super();
    }

    public Admin(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getChannelIds() {
        return channelIds;
    }

    public void setChannelIds(String channelIds) {
        this.channelIds = channelIds;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
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

    public String getCredentialsSalt() {
        return username + salt;
    }

    public List<String> getRoleIds() {
        return this.roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleIdsStr() {
        if (CollectionUtils.isEmpty(roleIds)) {
            return "";
        }
        StringBuilder s = new StringBuilder();
        for (String roleId : roleIds) {
            s.append(roleId);
            s.append(",");
        }
        return s.toString();
    }

    public void setRoleIdsStr(String roleIdsStr) {
        if (StringUtils.isEmpty(roleIdsStr)) {
            return;
        }
        String[] roleIdStrs = roleIdsStr.split(",");
        for (String roleIdStr : roleIdStrs) {
            if (StringUtils.isEmpty(roleIdStr)) {
                continue;
            }
            getRoleIds().add(roleIdStr);
        }
    }

    public List<String> getMerchantIds() {
        return merchantIds;
    }

    public void setMerchantIds(List<String> merchantIds) {
        this.merchantIds = merchantIds;
    }

    public String getMerchantIdsStr() {
        if (CollectionUtils.isEmpty(merchantIds)) {
            return "";
        }
        StringBuilder s = new StringBuilder();
        for (String merchantId : merchantIds) {
            s.append(merchantId);
            s.append(",");
        }
        return s.toString();
    }

    public void setMerchantIdsStr(String merchantIdsStr) {
        if (StringUtils.isEmpty(merchantIdsStr)) {
            return;
        }
        String[] merchantIdStrs = merchantIdsStr.split(",");
        for (String merchantIdStr : merchantIdStrs) {
            if (StringUtils.isEmpty(merchantIdStr)) {
                continue;
            }
            getMerchantIds().add(merchantIdStr);
        }
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Admin admin = (Admin) o;
        if (id != null ? !id.equals(admin.id) : admin.id != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return username;
    }
}
