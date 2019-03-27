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

/**
 * 暂不用
 */
//@Controller
@RequestMapping("payroll/yfbTransferNotify.do")
public class YfbTransferNotifyController extends YfbPayController {
    private static final Logger logger = LoggerFactory.getLogger(YsbPayController.class);
    private static final String ROUTER_KEY = "yfbPayroll";
    @Autowired
    ChannelService channelService;
    @Autowired
    YfbNotifyService yfbNotifyService;

    @RequestMapping(method = RequestMethod.POST)
    public void responseYfbTransferNotify(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //   request.setCharacterEncoding("");
        String content = request.getParameter("content");
        Channel channel = channelService.findByPayCost(ROUTER_KEY).get(0);
        if (attestation(request, channel)) {
            JSONObject params = JSONObject.parseObject(content);
            response.setContentType("text/html;charset=UTF-8");
            try {
                response.getWriter().print("true");
                logger.warn("接受上游通知验签成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
            yfbNotifyService.transferExecute(params);
        } else {
            logger.warn("上游通知验签失败");
        }


    }


}
