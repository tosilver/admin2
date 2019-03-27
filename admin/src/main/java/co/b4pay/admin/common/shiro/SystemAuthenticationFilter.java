package co.b4pay.admin.common.shiro;

import co.b4pay.admin.common.helper.LoginHelper;
import co.b4pay.admin.common.system.entity.Admin;
import co.b4pay.admin.common.system.service.AdminService;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 系统过滤器
 *
 * @author YK
 * @version $Id: SystemAuthenticationFilter.java, v 0.1 2016年6月24日 上午10:52:46 YK Exp $
 */
public class SystemAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(SystemAuthenticationFilter.class);

    @Autowired
    private AdminService adminService;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //        if (!allowed) {
//            String uri = WebUtils.toHttp(request).getRequestURI();
//            logger.info(uri);
//        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return super.onAccessDenied(request, response, mappedValue);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // logger.info("。。。登录成功。。。");
        // String username = (String) token.getPrincipal();
        Admin admin = adminService.getByUsername(token.getPrincipal().toString());
        org.springframework.web.util.WebUtils.setSessionAttribute(httpRequest, LoginHelper.LOGIN_ID_KEY, admin.getId());
        org.springframework.web.util.WebUtils.setSessionAttribute(httpRequest, LoginHelper.LOGIN_USERNAME_KEY, admin.getUsername());
        org.springframework.web.util.WebUtils.setSessionAttribute(httpRequest, LoginHelper.LOGIN_MERCHANT_IDS, admin.getMerchantIdsStr());
        org.springframework.web.util.WebUtils.setSessionAttribute(httpRequest, LoginHelper.LOGIN_ROLE_IDS, admin.getRoleIdsStr());
        // WebUtils.redirectToSavedRequest(request, response, getSuccessUrl());
        // WebUtils.issueRedirect(request, response, getSuccessUrl());
        // httpResponse.sendRedirect(getSuccessUrl());
        // return false;
        // return super.onLoginSuccess(token, subject, httpRequest, httpResponse);
        return true;
        //issueSuccessRedirect(request, response);
        //return true;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        if (e.getCause() != null && !e.getCause().getClass().isAssignableFrom(AuthenticationException.class)) {
            logger.error("登录出错：" + e.getMessage(), e);
        } else {
            logger.warn("登录失败：" + e.getMessage());
        }
        request.setAttribute(getUsernameParam(), token.getPrincipal());
        request.setAttribute("message", e.getLocalizedMessage());
        return super.onLoginFailure(token, e, request, response);
    }
}
