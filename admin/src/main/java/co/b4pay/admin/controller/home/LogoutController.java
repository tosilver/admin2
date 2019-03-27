package co.b4pay.admin.controller.home;

import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.core.constants.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author YK
 * @version $Id: LogoutController.java, v 0.1 2016年6月23日 下午3:25:43 YK Exp $
 */
@Controller
@RequestMapping("/logout")
public class LogoutController extends BaseController {

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(HttpServletRequest request) {
        return Constants.REDIRECT + "/login";
    }
}
