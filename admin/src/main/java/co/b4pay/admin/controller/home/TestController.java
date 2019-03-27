package co.b4pay.admin.controller.home;

import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.entity.base.AjaxResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static co.b4pay.admin.common.util.HtmlUtil.renderJson;

/**
 * @author YK
 * @version $Id v 0.1 2016年11月14日 16:08 Exp $
 */
@RestController
@RequestMapping("/test/mq")
public class TestController extends BaseController {

    //@Autowired
    //private MessageProducer messageProducer;

    @RequestMapping(method = RequestMethod.GET)
    public void index(HttpServletResponse response) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("mobile", "13652328652");
//        map.put("content", "您的短信是:" + UUID.randomUUID());
//        messageProducer.sendMessage(MessageType.CERTIFY_AUDIT_NOTICE, map);
//        ApiResult apiResult = TemplateMsgApi.send();

        renderJson(response, AjaxResponse.success());
    }
}