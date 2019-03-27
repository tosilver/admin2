package co.b4pay.admin.common.helper;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.subject.WebSubject;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录信息帮助类
 *
 * @author YK
 * @version $Id: LoginHelper.java, v 0.1 2016年5月20日 下午10:28:08 YK Exp $
 */
public class LoginHelper {
    // public static final String LOGIN_ID_KEY = "user_id";

    public static final String LOGIN_ID_KEY = "user_id";
    public static final String LOGIN_USERNAME_KEY = "user_name";
    public static final String LOGIN_MERCHANT_IDS = "merchant_ids";
    public static final String LOGIN_ROLE_IDS = "role_ids";

    /**
     * 获取登录用户ID
     *
     * @return
     */
    public static String getId() {
        WebSubject webSubject = (WebSubject) SecurityUtils.getSubject();
        if (webSubject == null) {
            return null;
        }
        Object userId = WebUtils.getSessionAttribute((HttpServletRequest) webSubject.getServletRequest(), LOGIN_ID_KEY);
        if (userId != null) {
            return userId.toString();
        }
        return null;
    }

    /**
     * 获取登录用户的角色ID
     *
     * @return
     */
    public static String getRoleIds() {
        WebSubject webSubject = (WebSubject) SecurityUtils.getSubject();
        if (webSubject == null) {
            return null;
        }
        Object roleIds = WebUtils.getSessionAttribute((HttpServletRequest) webSubject.getServletRequest(), LOGIN_ROLE_IDS);
        if (roleIds != null) {
            return roleIds.toString();
        } else {
            return null;
        }
    }

    /**
     * 获取登录用户的商户ID
     *
     * @return
     */
    public static String getMerchantIds() {
        WebSubject webSubject = (WebSubject) SecurityUtils.getSubject();
        if (webSubject == null) {
            return null;
        }
        Object merchantIds = WebUtils.getSessionAttribute((HttpServletRequest) webSubject.getServletRequest(), LOGIN_MERCHANT_IDS);
        if (merchantIds != null) {
            return merchantIds.toString();
        } else {
            return null;
        }
    }

    /**
     * 获取登录用户名
     *
     * @return
     */
    public static String getUsername() {
        // HttpServletRequest request = ((ServletRequestAttributes)
        // RequestContextHolder.getRequestAttributes()).getRequest();

        WebSubject webSubject = (WebSubject) SecurityUtils.getSubject();
        if (webSubject == null) {
            return null;
        }
        Object username = WebUtils.getSessionAttribute((HttpServletRequest) webSubject.getServletRequest(), LOGIN_USERNAME_KEY);
        if (username != null) {
            return username.toString();
        }
        username = webSubject.getPrincipal();
        if (username != null) {
            return username.toString();
        }
        return null;
    }

    /**
     * 是否登录
     *
     * @return
     */
    public static boolean isLogined() {
        String username = getUsername();
        return username != null && !username.isEmpty();
    }

}
