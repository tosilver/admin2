package co.b4pay.admin.controller.system;

import co.b4pay.admin.common.helper.LoginHelper;
import co.b4pay.admin.common.system.entity.Admin;
import co.b4pay.admin.common.system.service.AdminService;
import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.controller.Utils.Utils;
import co.b4pay.admin.entity.AdminChannel;
import co.b4pay.admin.entity.SysShangyou;
import co.b4pay.admin.service.ShangyouService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/shangyou")
public class ShangyouController {
    //todo

    @Autowired
    private ShangyouService shangyouService;

    @Autowired
    private AdminService adminService;

    @RequiresPermissions("shangyou:view")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("adminId", LoginHelper.getId());
        Admin admin = adminService.get(LoginHelper.getId());
        //不是超级管理员进来
        if (null != admin && !(admin.getRoleIds().contains("1"))) {
            List<String> merchantIdList = admin.getMerchantIds();
            //根据商户id查找管理员账号(终端客户管理账号)
            List<Admin> adminList = new ArrayList<Admin>();
            adminList.add(admin);
            for (int i = 0; i < merchantIdList.size(); i++) {
                Admin tempAdmin = adminService.findByMerchantId(merchantIdList.get(i) + ",");
                if (null != tempAdmin) {
                    adminList.add(tempAdmin);
                }
            }
            model.addAttribute("adminList", adminList);
            return "system/adminList";
        }

        model.addAttribute("adminList", adminService.findAll());
        return "system/adminList";
    }

    @RequiresPermissions("shangyou:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(SysShangyou sysShangyou, RedirectAttributes redirectAttributes) {
        System.out.println(sysShangyou);
        shangyouService.createSYUser(sysShangyou);
        redirectAttributes.addFlashAttribute("msg", "新增成功");
        return "redirect:/shangyou";
    }
}
