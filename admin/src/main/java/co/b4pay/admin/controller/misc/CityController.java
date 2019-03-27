package co.b4pay.admin.controller.misc;

import co.b4pay.admin.service.CityService;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.entity.City;
import co.b4pay.admin.common.util.StringUtil;
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
 * 城市Controller
 * Created by john on 2017/8/2.
 */
@Controller
@RequestMapping("city")
public class CityController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "getByProvinceId", method = RequestMethod.GET)
    public @ResponseBody
    List<City> getByProvinceId(String provinceId) {
        if (StringUtil.isBlank(provinceId)) {
            return Collections.emptyList();
        }
        List<City> list = cityService.getByProvinceId(provinceId);
        return list;
    }
}
