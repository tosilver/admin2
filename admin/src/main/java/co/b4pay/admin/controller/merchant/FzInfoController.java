package co.b4pay.admin.controller.merchant;

import co.b4pay.admin.common.helper.LoginHelper;
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
@RequestMapping("fzInfo")
public class FzInfoController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(FzInfoController.class);

    @Autowired
    private ConsumeService consumeService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private RouterService routerService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private TransinService transinService;

    private Page<Consume> pac = new Page<>();

    //    @Autowired
    private HmacSHA1Signature signature = new HmacSHA1Signature();

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @RequiresPermissions("fzInfo:list")
    public String list(Model model, @PageAttribute Page<Consume> page, HttpServletRequest request) {

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
        p.add("tradeState", "1");
        page.setParams(p);

        if (roleIds.contains("1") || roleIds.contains("2")) {   //如果拥有超级管理员权限
            Params params = page.getParams();
            Page<Consume> consumePage = consumeService.findPage(page);
            List<Consume> consumeList = consumePage.getList();
            List<Map<String, Object>> mapList = new ArrayList<>();
            for (Consume consume : consumeList) {
                Map<String, Object> map = new HashMap<>();
                Long transinId = consume.getTransinId();
                map.put("consume", consume);
                if (transinId != null) {
                    Transin transin = transinService.get(transinId.toString());
                    map.put("transin", transin.getRealname());
                } else {
                    map.put("transin", "");
                }
                mapList.add(map);
            }
            model.addAttribute("page", consumePage);
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
            model.addAttribute("merchantList", merchantService.findList());
            model.addAttribute("transinList", transinService.findList());
        }
        //consumeService
        //统计金额
        Double fzAmount = consumeService.sumFzMoney(page);
        Integer fzCount = consumeService.accountCount(page) == null ? 0 :
                consumeService.fzCount(page);
        Integer failCount = consumeService.fzFailCount(page) == null ? 0 :
                consumeService.fzFailCount(page);
        String accountMoneyResult = "";
        if (fzAmount != null) {
            String str = new DecimalFormat("0.00").format(fzAmount);
            model.addAttribute("fzAmount", str);
            model.addAttribute("fzCount", fzCount);
            model.addAttribute("failCount", failCount);
        } else {
            Double d = consumeService.sumFzMoney(page);
            if (d == null) {
                d = 0D;
            }
            model.addAttribute("fzMoney", Utils.getBigDecimal(d));
            model.addAttribute("fzCount", fzCount);
            model.addAttribute("failCount", failCount);
        }
        //
        if (page.getParams() != null) {
            Params params = new Params();
            if (page.getParams().containsKey("endDate")) {
                params.put("endDate", page.getParams().getString("endDate"));
            }
            if (page.getParams().containsKey("startDate")) {
                params.put("startDate", page.getParams().getString("startDate"));
            }
        }
        return "fzInfo/fzInfoList";
    }

    @RequestMapping(value = "form")
    @RequiresPermissions("fzInfo:form")
    public String form(Model model, String id) {
        model.addAttribute("consume", consumeService.get(id));
        return "fzInfo/fzInfoForm";
    }

    @RequestMapping(value = "updateStatus", method = RequestMethod.GET)
    public String updateStatus(RedirectAttributes redirectAttributes, String id, int status, String amount) {

        try {

            if (amount.trim().equals("")) {
                addMessage(redirectAttributes, "数据有误，请重新输入");
                return "redirect:/fzInfo/list";
            }
            consumeService.updateStatus(id, status, getBigDecimal(amount));

            Consume trade = consumeService.get(id);

            JSONObject returnData = new JSONObject();
            returnData.put("tradeNo", trade.getMerchantOrderNo());
            returnData.put("amount", trade.getTotalAmount().toPlainString());
            returnData.put("tradeState", String.valueOf(trade.getTradeState()));
            returnData.put("merchantId", trade.getMerchant().getId());
            returnData.put("payTime", String.valueOf(trade.getUpdateTime().getTime()));
//        returnData.put("tradeId",id);

            Merchant m = merchantService.get(trade.getMerchant().getId());

            MerchantService ms = new MerchantService();
            //签名记得传
            String content = SignatureUtil.getSignatureContent(returnData, true);
            String sign = signature.sign(content, m.getSecretKey(), "UTF-8");
            returnData.put("signature", sign);
            consumeService.updateTrade(id, returnData.toString(), 0);

            addMessage(redirectAttributes, "订单更改成功");
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
        return "redirect:/fzInfo/list";
    }

    //@PageAttribute Page<Consume> page
    @RequestMapping(value = "derived", method = RequestMethod.POST)
    public void derived(HttpServletRequest request,
                        HttpServletResponse response, @PageAttribute Page<Consume> page) {
        String merchantIds = LoginHelper.getMerchantIds();
        String roleIds = LoginHelper.getRoleIds();
        Params params = Params.create();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramertName = parameterNames.nextElement();
            String[] parameterValues = request.getParameterValues(paramertName);
            params.add(paramertName, parameterValues[0]);
        }

        if (roleIds.contains("1") || roleIds.contains("2")) {

        } else if (request.getParameter("merchantId").toString().trim().isEmpty()) {
            params.add("merchantIds", merchantIds.substring(0, merchantIds.length() - 1));
        }

        String templatePath = this.getClass().getClassLoader().getResource("/ExportInfo.xml").getPath();

        pac.setPageSize(9999999);
        pac.setPageFirst(0);
        List<Object> consumeDTOS = toConsumeDTO(consumeService.findPage(pac).getList());


        try {
            ExcelTemplateUtil.exportExcel(templatePath, "xlsx", consumeDTOS, request, response);
        } catch (Exception e) {
            logger.error("文件导出异常：>" + e.getMessage());
            e.printStackTrace();
        }

    }

    public static List<Object> toConsumeDTO(List<Consume> consumes) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Object> consumeDTOS = new ArrayList<>();
        for (int i = 0; i < consumes.size(); i++) {
            ConsumeDTO consumeDTO = new ConsumeDTO();
            Consume consume = consumes.get(i);
            consumeDTO.setMerchantOrderNo(consume.getMerchantOrderNo());
            try {
                String name = consume.getRouter().getName();
                consumeDTO.setName(name);
            } catch (NullPointerException e) {
                consumeDTO.setName("无");
            }

            consumeDTO.setAccountAmount(consume.getAccountAmount());
            consumeDTO.setTotalAmount(consume.getTotalAmount());
            Date createTime = consume.getCreateTime();
            String time = sdf.format(createTime);
            consumeDTO.setCreateTime(time);
            Integer status = consume.getStatus();
            if (status == 0) {
                consumeDTO.setStatus("调用失败");
            } else {
                consumeDTO.setStatus("调用成功");
            }
            String trade = null;
            switch (consume.getTradeState()) {
                case -2:
                    trade = "交易关闭";
                    break;
                case -1:
                    trade = "支付失败";
                    break;
                case 0:
                    trade = "未支付";
                    break;
                case 1:
                    trade = "支付成功";
                    break;
                case 2:
                    trade = "人工确认支付";
                    break;
            }
            consumeDTO.setTradeState(trade);
            consumeDTOS.add(consumeDTO);
        }

        return consumeDTOS;
    }

    public String sub(String d1, String d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        BigDecimal subtract = b1.subtract(b2);
        return subtract.toPlainString();
    }

    public BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        }
        return ret;
    }
}
