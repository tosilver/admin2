package co.b4pay.admin.common.shiro.realm;

import co.b4pay.admin.common.system.entity.Admin;
import co.b4pay.admin.common.system.service.AdminService;

import co.b4pay.admin.common.util.NumberUtil;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户认证器
 *
 * @author cc
 */
public class SystemRealm extends AuthorizingRealm {
    @Autowired
    private AdminService adminService;

    // 权限验证
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(adminService.findRoles(username));
        authorizationInfo.setStringPermissions(adminService.findPermissions(username));
        return authorizationInfo;
    }

    // 登录验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        try {
            String username = (String) token.getPrincipal();
            Admin admin = adminService.getByUsername(username);

            if (admin == null) {
                throw new UnknownAccountException("无此用户");// 没找到帐号
            }

            if (NumberUtil.equals(0, admin.getStatus())) {
                throw new LockedAccountException("帐号锁定"); //
            }

            // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(admin.getUsername(), // 用户名
                    admin.getPassword(), // 密码
                    ByteSource.Util.bytes(admin.getCredentialsSalt()), // salt=username+salt
                    getName() // realm name
            );
            return authenticationInfo;
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new AuthenticationException("获取授权信息时出错！", e);
        }
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
