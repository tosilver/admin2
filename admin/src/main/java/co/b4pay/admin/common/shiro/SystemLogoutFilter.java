package co.b4pay.admin.common.shiro;

/**
 * 退出登陆过滤器
 *
 * @author cc
 */
public class SystemLogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {

    // @Override
    // protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws
    // Exception {
    // Session session = getSubject(request, response).getSession(false);
    // if (session == null) {
    // return true;
    // }
    // return session.getAttribute(MainConfig.SESSION_FORCE_LOGOUT_KEY) == null;
    // }

    // @Override
    // protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
    // try {
    // getSubject(request, response).logout();// 强制退出
    // } catch (Exception e) {
    // /* ignore exception */}
    //
    // String loginUrl = getLoginUrl() + (getLoginUrl().contains("?") ? "&" : "?") + "forceLogout=1";
    // WebUtils.issueRedirect(request, response, loginUrl);
    // return false;
    // }
}
