/**
 *
 */
package co.b4pay.admin.common.captcha;

import co.b4pay.admin.common.core.config.MainConfig;
import co.b4pay.admin.common.core.constants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码校验器
 *
 * @author YK
 * @version $Id: CaptchaValidator.java, v 0.1 2012-5-2 下午3:43:08 YK Exp $
 */
public class CaptchaValidator {

    /**
     * 验证
     *
     * @param request
     * @return
     */
    public boolean validate(HttpServletRequest request) {
        String captcha = request.getParameter(Constants.CAPTCHA);
        // 开发模式方便测试
        if (MainConfig.isDevMode && "8888".equalsIgnoreCase(captcha)) {
            return true;
        }
        if (StringUtils.isBlank(captcha)) {
            return false;
        }
        String vCode = (String) WebUtils.getSessionAttribute(request, Constants.CAPTCHA);
        WebUtils.setSessionAttribute(request, Constants.CAPTCHA, null);// 使验证码失效
        return StringUtils.equalsIgnoreCase(captcha, vCode);
    }
}
