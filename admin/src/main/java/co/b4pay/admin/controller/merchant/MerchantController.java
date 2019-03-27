package co.b4pay.admin.controller.merchant;

import co.b4pay.admin.common.biz.exception.BizException;
import co.b4pay.admin.common.core.signature.Md5Encrypt;
import co.b4pay.admin.common.helper.LoginHelper;
import co.b4pay.admin.common.system.entity.Admin;
import co.b4pay.admin.common.system.service.AdminService;
import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.web.PageAttribute;
import co.b4pay.admin.entity.Merchant;
import co.b4pay.admin.entity.base.AjaxResponse;
import co.b4pay.admin.entity.base.DtoException;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.entity.enums.AccountType;
import co.b4pay.admin.entity.enums.SettleType;
import co.b4pay.admin.service.ChannelService;
import co.b4pay.admin.service.MerchantRateService;
import co.b4pay.admin.service.MerchantService;
import co.b4pay.admin.service.ProvinceService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商户 Controller
 * Created by john on 2018/4/23.
 */
@Controller
@RequestMapping("merchant")
public class MerchantController extends BaseController {
    /**
     * 日志对象
     */
    private Logger logger = LoggerFactory.getLogger(MerchantController.class);

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private MerchantRateService merchantRateService;
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
//    @RequestMapping(method = RequestMethod.GET)
//    @RequiresPermissions("merchant:list")
    public String list(Model model, @PageAttribute Page<Merchant> page) {
        String roleIds = LoginHelper.getRoleIds();
        if (!(roleIds.contains("1"))) {      //说明不是超级管理员角色
            String merchantIds = LoginHelper.getMerchantIds();
            if (StringUtil.isNoneBlank(merchantIds)) {
                Params params = page.getParams();
                if (null == params) {
                    params = Params.create("merchantIds", merchantIds.split(","));
                } else {
                    params.put("merchantIds", merchantIds.split(","));
                }
                page.setParams(params);
                model.addAttribute("page", merchantService.findPage(page));
                model.addAttribute("provinceList", provinceService.findList());
            }
//            model.addAttribute("page", merchantService.findPage(page));
//            model.addAttribute("provinceList", provinceService.findList());
            return "merchant/merchantList";
        }
        model.addAttribute("page", merchantService.findPage(page));
        model.addAttribute("provinceList", provinceService.findList());
        return "merchant/merchantList";
    }


    @RequestMapping(value = "update", method = RequestMethod.GET)
    @RequiresPermissions("merchant:form")
    public String update(Model model, String id) {
        model.addAttribute("channelList", channelService.findList());
        model.addAttribute("provinceList", provinceService.findList());
        model.addAttribute("accountTypes", AccountType.values());
        model.addAttribute("settleTypes", SettleType.values());
        model.addAttribute("merchant", merchantService.get(id));
        return "merchant/merchantForm";
    }

    @RequestMapping(value = "form", method = RequestMethod.GET)
    @RequiresPermissions(value = {"merchant:form", "merchantCentre:info"}, logical = Logical.OR)
    public String form(Model model, String id) {
        model.addAttribute("channelList", channelService.findList());
        model.addAttribute("provinceList", provinceService.findList());
        model.addAttribute("accountTypes", AccountType.values());
        model.addAttribute("settleTypes", SettleType.values());
        if (StringUtils.isNoneBlank(id)) {
            model.addAttribute("merchant", merchantService.get(id));
            model.addAttribute("merchantRateList", merchantRateService.findByMerchantId(id));
            return "merchant/merchantInfo";
        }
        return "merchant/merchantForm";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions("merchant:save")
    public String save(RedirectAttributes redirectAttributes, Merchant merchant) {
        if (StringUtil.isBlank(merchant.getId())) {
            int isExists = merchantService.findByCompany(merchant.getCompany());
            if (isExists > 0) {
                addErrorMessage(redirectAttributes, "商户(公司名)已经存在，请勿重复添加");
                return "redirect:/merchant/list";
            }
            merchantService.save(merchant);
            Admin admin = adminService.get(LoginHelper.getId());
            if (null != admin && !(admin.getRoleIds().contains("1"))) {
                List<String> merchantIds = admin.getMerchantIds();
                merchantIds.add(merchant.getId());
                admin.setMerchantIds(merchantIds);
                adminService.update(admin);
            }
        } else {
            merchantService.update(merchant);
        }
        return "redirect:/merchant/list";
    }

    @RequestMapping(value = "account", method = RequestMethod.GET)
    @RequiresPermissions("merchant:account")
    public String account(Model model, String id) {
        return "merchant/accountForm";
    }

    @RequestMapping(value = "updateStatus", method = RequestMethod.GET)
    public String updateStatus(RedirectAttributes redirectAttributes, String id, int status) {
        merchantService.updateStatus(id, status);
        addMessage(redirectAttributes, "状态更改成功");
        return "redirect:/merchant/list";
    }

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public String merchantInfo(Model model, @PageAttribute Page<Merchant> page) {
        String merchantIds = LoginHelper.getMerchantIds();
        if (StringUtil.isNoneBlank(merchantIds)) {
            Params params = page.getParams();
            if (null == params) {
                params = Params.create("merchantIds", merchantIds.split(","));
            } else {
                params.put("merchantIds", merchantIds.split(","));
            }
            page.setParams(params);
            model.addAttribute("page", merchantService.findPage(page));
        }
        return "merchant/merchantList";
    }

    @RequestMapping(value = "selMoney", method = RequestMethod.GET)
    public @ResponseBody
    AjaxResponse selMoney(HttpServletRequest request) {
        if (request.getParameter("id").trim() == "" || request.getParameter("mac").trim() == "") {
            return AjaxResponse.failure(1001, "参数不能为空");
        } else if (!idMd5(request.getParameter("id")).equals(request.getParameter("mac"))) {
            return AjaxResponse.failure(1002, "验签失败");
        }
        BigDecimal bd = merchantService.findByBalance(request.getParameter("id"));
        if (bd == null || bd.equals(null)) {
            return AjaxResponse.failure(1003, "请输入正确的商户号");
        }
//        idMd5(request.getParameter("id")).equals(request.getParameter("mac"));
        Map map = new HashMap();
        map.put("id", request.getParameter("id"));
        map.put("balance", bd);
        return AjaxResponse.success(map);
    }

    protected String idMd5(String id) {
        String md5 = null;
        try {
            md5 = Md5Encrypt.md5(id).toUpperCase();
        } catch (BizException e) {
            logger.warn(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return md5;
    }

}
