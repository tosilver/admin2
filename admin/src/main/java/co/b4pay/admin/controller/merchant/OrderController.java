package co.b4pay.admin.controller.merchant;

import co.b4pay.admin.common.helper.LoginHelper;
import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.util.myutil.file.excel.ExcelTemplateUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.web.PageAttribute;
import co.b4pay.admin.controller.Utils.HmacSHA1Signature;
import co.b4pay.admin.controller.Utils.SignatureUtil;
import co.b4pay.admin.controller.Utils.Utils;
import co.b4pay.admin.entity.*;
import co.b4pay.admin.entity.base.DtoException;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.service.*;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

//import com.suning.epps.merchantsignature.SignatureUtil;

/**
 * 交易记录Controller
 * Created by john on 2018/4/27.
 */
@Controller
@RequestMapping("order")
public class OrderController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private TUserService tUserService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private RouterService routerService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private MerchantRateService merchantRateService;
    @Autowired
    private YfbPayrollService yfbPayrollService;

    private Page<Order> pac = new Page<>();


    @RequestMapping(value = "list", method = RequestMethod.GET)
    @RequiresPermissions("order:list")
    public String list(Model model, @PageAttribute Page<Order> page, HttpServletRequest request) {

        String roleIds = LoginHelper.getRoleIds();
        Params p = page.getParams();
        String startDate = null;
        if (p != null) {
            startDate = p.containsKey("startDate") ? p.getString("startDate") : null;
        } else {
            p = new Params();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isBlank(startDate)) {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) - 2);
            startDate = sdf.format(c.getTime());
            p.put("startDate", startDate);
        }
        page.setParams(p);
        if (roleIds.contains("1") || roleIds.contains("2")) {   //如果拥有超级管理员权限
            Params params = page.getParams();
            Page<Order> orderPage = orderService.findPage(page);
            List<Order> consumeList = orderPage.getList();
            List<Map<String, Object>> mapList = new ArrayList<>();
            for (Order order : consumeList) {
                Map<String, Object> map = new HashMap<>();
                map.put("order", order);
                mapList.add(map);
            }
            model.addAttribute("page", orderPage);
            model.addAttribute("vo", mapList);
            pac = page;
            if (params != null) {
                try {
                    String routerId = params.getString("routerId");
                    if (routerId != null && routerId != "") {
                        List<Channel> channelList = channelService.findByPayCost(routerId);
                        model.addAttribute("channelList", channelList);
                    }
                } catch (DtoException e) {
                    model.addAttribute("channelList", channelService.findList());
                }
            } else {
                model.addAttribute("channelList", channelService.findList());
            }
            model.addAttribute("userList", tUserService.findList());
            //model.addAttribute("transinList", transinService.findList());
        }
        //orderService
        //统计金额
        Double amount = orderService.sumMoney(page);
        //统计已付金额
        Double accountMoney = orderService.sumAccountMoney(page) == null ? 0D :
                orderService.sumAccountMoney(page);
        //统计总个数
        Integer orderCount = orderService.count(page);
        //统计已付金额个数
        Integer sumAccountCount = orderService.sumAccountCount(page) == null ? 0 :
                orderService.sumAccountCount(page);
        if (amount != null && accountMoney != null) {
            String str = new DecimalFormat("0.00").format(amount);
            String accountMoneyStr = new DecimalFormat("0.00").format(accountMoney);
            model.addAttribute("amount", str);
            model.addAttribute("accountMoneyStr", accountMoneyStr);
            model.addAttribute("orderCount", orderCount);
            model.addAttribute("sumAccountCount", sumAccountCount);
        } else {
            Double d = orderService.sumMoney(page);
            if (d == null) {
                d = 0D;
            }
            Double od = orderService.sumAccountMoney(page);
            if (od == null) {
                od = 0D;
            }
            model.addAttribute("amount", Utils.getBigDecimal(d));
            model.addAttribute("sumAccountMoney", Utils.getBigDecimal(od));
            model.addAttribute("orderCount", orderCount);
            model.addAttribute("sumAccountCount", sumAccountCount);
        }
        return "merchant/orderList";
    }


    @RequestMapping(value = "form")
    @RequiresPermissions("order:form")
    public String form(Model model, String id) {
        model.addAttribute("order", orderService.get(id));
        return "merchant/orderForm";
    }

    @RequestMapping(value = "updateStatus", method = RequestMethod.GET)
    public String updateStatus(RedirectAttributes redirectAttributes, String id, int status, Double payedMoney) {
        orderService.updateStatus(id, status, payedMoney);
        /*Order order=new Order();
        order.setOrderId(id);
        BigDecimal bigDecimalAmount = Utils.getBigDecimal(amount);
        order.setPayedMoney(bigDecimalAmount);
        orderService.update(order);*/
        return "redirect:/order/list";
    }


    public String sub(String d1, String d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        BigDecimal subtract = b1.subtract(b2);
        return subtract.toPlainString();
    }

}
