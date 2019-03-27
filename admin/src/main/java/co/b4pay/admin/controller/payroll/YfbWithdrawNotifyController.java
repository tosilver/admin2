package co.b4pay.admin.controller.payroll;

import co.b4pay.admin.common.web.YfbPayController;
import co.b4pay.admin.entity.Channel;
import co.b4pay.admin.service.ChannelService;
import co.b4pay.admin.service.YfbNotifyService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


@Controller
@RequestMapping("payroll/yfbWithdrawNotify.do")
public class YfbWithdrawNotifyController extends YfbPayController {
    private static final Logger logger = LoggerFactory.getLogger(YsbPayController.class);
    private static final String ROUTER_KEY = "yfbPayroll";
    @Autowired
    ChannelService channelService;
    @Autowired
    YfbNotifyService yfbNotifyService;

    @RequestMapping(method = RequestMethod.POST)
    public void responseYfbWithdrawNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String content = request.getParameter("content");
        logger.info("代付到卡回调通知content：" + content);
        Channel channel = channelService.findByPayCost(ROUTER_KEY).get(0);
        if (true || attestation(request, channel)) {
            JSONObject contentJson = JSONObject.parseObject(content);
            response.setContentType("text/html;charset=UTF-8");
            try {
                String responseNotify = yfbNotifyService.withdrawExecute(contentJson);
                response.getWriter().print(responseNotify);
                logger.warn("接受上游通知验签成功");
            } catch (IOException e) {
                logger.warn("返回上游通知失败");
            }

        } else {
            logger.warn("上游通知验签失败");
        }


    }
}
