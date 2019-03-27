package co.b4pay.admin.controller.partner;

import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.web.PageAttribute;
import co.b4pay.admin.entity.Channel;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.service.ChannelService;
import co.b4pay.admin.service.GoodsTypeService;
import co.b4pay.admin.service.RouterService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 渠道 Controller
 * Created by john on 2018/4/27.
 */
@Controller
@RequestMapping("channel")
public class ChannelController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(ChannelController.class);

    @Autowired
    private ChannelService channelService;

    @Autowired
    private RouterService routerService;

    @Autowired
    private GoodsTypeService goodsTypeService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @RequiresPermissions("channel:list")
    public String list(Model model, @PageAttribute Page<Channel> page) {

        model.addAttribute("routerList", routerService.findList());
        model.addAttribute("page", channelService.findPage(page));
        return "channel/channelList";
    }

    @RequestMapping("form")
    @RequiresPermissions("channel:form")
    public String form(Model model, String id) {
        if (StringUtil.isNoneBlank(id)) {
            model.addAttribute("channel", channelService.get(id));
        }
        model.addAttribute("routerList", routerService.findList());
        model.addAttribute("goodsTypeList", goodsTypeService.findList());
        return "channel/channelForm";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions(value = "channel:save")
    public String save(RedirectAttributes redirectAttributes, Channel channel) {
        int count = 0;
        if (StringUtil.isNoneBlank(channel.getId())) {
            count = channelService.update(channel);
        } else {
            count = channelService.save(channel);
        }
        if (count > 0) {
            redirectAttributes.addAttribute("msg", "操作成功");
        } else {
            redirectAttributes.addFlashAttribute("msg", "操作失败");
        }
        return "redirect:/channel/list";
    }

    @RequestMapping(value = "updateStatus", method = RequestMethod.GET)
    public String updateStatus(RedirectAttributes redirectAttributes, String id, int status) {
        if (channelService.updateStatus(id, status) > 0) {
            addMessage(redirectAttributes, "更改状态成功");
        } else {
            addMessage(redirectAttributes, "状态更改失败");
        }
        return "redirect:/channel/list";
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String delete(RedirectAttributes redirectAttributes, String id) {
        if (channelService.updateStatus(id, -2) > 0) {
            addMessage(redirectAttributes, "渠道删除成功");
        } else {
            addMessage(redirectAttributes, "渠道删除失败");
        }
        return "redirect:/channel/list";
    }
}
