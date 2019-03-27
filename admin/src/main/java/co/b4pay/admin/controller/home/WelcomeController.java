package co.b4pay.admin.controller.home;

import co.b4pay.admin.common.helper.LoginHelper;
import co.b4pay.admin.common.system.service.AdminService;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.service.ConsumeService;
import co.b4pay.admin.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by john on 2018/1/2.
 */
@Controller
@RequestMapping("/welcome")
public class WelcomeController extends BaseController {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private ConsumeService consumeService;

    private static final int ARRAY_LEN = 7;        //展示数组的长度

    @RequestMapping(method = RequestMethod.GET)
    public String welcome(Model model) {

        String roleIds = LoginHelper.getRoleIds();
        String id = LoginHelper.getId();
        if (!(roleIds.contains("1"))) {      //说明不是超级管理员角色
            return "redirect:/merchant/info";
        }

        Calendar calendar = Calendar.getInstance();
        //商户日增长数量
        Map<String, Object[]> map = new HashMap<String, Object[]>();

        Integer[] merchantIncrease = new Integer[ARRAY_LEN];
        Integer[] days = new Integer[ARRAY_LEN];                //日期数组
        Double[] tradeMoneys = new Double[ARRAY_LEN];         //交易金额
        Integer[] tradeCounts = new Integer[ARRAY_LEN];         //交易笔数

        for (int i = 0; i < merchantIncrease.length; i++) {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            merchantIncrease[i] = merchantService.selectIncreaseByDate(year, month, day);
            tradeCounts[i] = consumeService.countByStatusAndDate(1, year, month, day);
            tradeMoneys[i] = consumeService.sumMoneyByStatusAndDate(1, year, month, day);

            days[i] = day;
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        }
        map.put("merchantIncrease", merchantIncrease);
        map.put("days", days);
        map.put("tradeMoneys", tradeMoneys);
        map.put("tradeCounts", tradeCounts);
        model.addAttribute("map", map);
        return "welcome";
    }

}
