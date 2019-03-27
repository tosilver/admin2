package co.b4pay.admin.controller.misc;

import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.entity.SysArea;
import co.b4pay.admin.service.SysAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

/**
 * 地区Controller
 * Created by liuwei on 2017/8/2.
 */
@Controller
@RequestMapping("sysArea")
public class SysAreaController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(SysAreaController.class);

    @Autowired
    private SysAreaService sysAreaService;

    @RequestMapping(value = "getByCityId", method = RequestMethod.GET)
    public @ResponseBody
    List<SysArea> getByCityId(String cityId) {
        if (StringUtil.isBlank(cityId)) {
            return Collections.emptyList();
        }
        return sysAreaService.getByCityId(cityId);
    }
}
