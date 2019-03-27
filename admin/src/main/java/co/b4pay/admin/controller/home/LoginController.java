package co.b4pay.admin.controller.home;

import co.b4pay.admin.common.helper.LoginHelper;
import co.b4pay.admin.common.constants.LinkConstants;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.core.constants.Constants;
import co.b4pay.admin.common.util.StringUtil;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录控制器
 *
 * @author mac
 */
@Controller
@RequestMapping({"/", "/login"})
public class LoginController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    // @Autowired
    // private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String doGet() {
        // if (logger.isInfoEnabled()) {
        // logger.info("[GET] SID: " + WebUtils.getSessionId(org.apache.shiro.web.util.WebUtils.toHttp(request)) +
        // ";username=" + LoginHelper.getUsername());
        // }
        if (LoginHelper.isLogined()) {
            return Constants.REDIRECT + LinkConstants.INDEX;
        }
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(HttpServletRequest request, Model model) {
//        if (logger.isInfoEnabled()) {
//            logger.info("[POST] SID: " + WebUtils.getSessionId(org.apache.shiro.web.util.WebUtils.toHttp(request)) + ";username=" + LoginHelper.getUsername());
//        }
        ShiroHttpServletRequest shiroRequest = (ShiroHttpServletRequest) request;
        // Enumeration<String> en = shiroRequest.getAttributeNames();
        // while (en.hasMoreElements()) {
        // String string = (String) en.nextElement();
        // System.out.println(string);
        // }
        String exceptionClassName = (String) shiroRequest.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);// "shiroLoginFailure"
        String error = StringUtil.EMPTY;
        if (StringUtil.isNotBlank(exceptionClassName)) {
            //exceptionClassName不为空则说明登录失败
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
                error = "用户名/密码错误,错误3次将被锁定";
            } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
                error = "用户名/密码错误,错误3次将被锁定";
            } else {
                error = "其他错误：" + exceptionClassName + ",请联系管理员";
            }
        } else if (request.getAttribute("message") != null) {
            //message不为空则说明登录失败
            error = String.valueOf(request.getAttribute("message"));
        }

        if (StringUtil.isNotBlank(error)) {
            model.addAttribute("message", error);
            return "login";
        }
        if (request.getParameter("forceLogout") != null) {
            model.addAttribute("message", "您已经被管理员强制退出，请重新登录");
            return "login";
        }

        return Constants.REDIRECT + LinkConstants.INDEX;
    }

}
