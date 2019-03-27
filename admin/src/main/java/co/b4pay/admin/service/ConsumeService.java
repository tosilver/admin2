package co.b4pay.admin.service;

import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.common.util.DateUtil;
import co.b4pay.admin.controller.Utils.Utils;
import co.b4pay.admin.dao.ConsumeDao;
import co.b4pay.admin.entity.Consume;
import co.b4pay.admin.entity.JobTrade;
import co.b4pay.admin.entity.base.DtoException;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 消费记录Service
 *
 * @author YK
 * @version $Id: ConsumeService.java, v 0.1 2018年4月21日 上午11:45:58 YK Exp $
 */
@Service
@Transactional
public class ConsumeService extends CrudService<ConsumeDao, Consume> {
    @Autowired
    ConsumeDao consumeDao;

    public Double sumFzMoney(Page<Consume> page) {
        Params params = page.getParams();
        if (params == null) {
            params = Params.create();
        }
        return dao.sumFzMoney(params);
    }

    public Integer fzCount(Page<Consume> page) {
        Params params = page.getParams();
        if (params == null) {
            params = Params.create();
        }
        return dao.fzCount(params);
    }

    public Integer fzFailCount(Page<Consume> page) {
        Params params = page.getParams();
        if (params == null) {
            params = Params.create();
        }
        return dao.fzFailCount(params);
    }

    public Double sumMoney(Page<Consume> page) {
        Params params = page.getParams();
        if (params == null) {
            params = Params.create();
        }
        return dao.sumMoney(params);
    }

    public Double sumRequestMoney(Page<Consume> page) {
        Params params = page.getParams();
        if (params == null) {
            params = Params.create();
        }
        return dao.sumRequestMoney(params);
    }

    public Double getSuccessRate(Page<Consume> page) {
        Params params = page.getParams();
        if (params == null) {
            params = Params.create();
        }
        return dao.getSuccessRate(params);
    }

    public Double sumAccountMoney(Page<Consume> page) {
        Params params = page.getParams();
        if (params == null) {
            params = Params.create();
        }
        return dao.sumAccountMoney(params);
    }

    public Integer accountCount(Page<Consume> page) {
        Params params = page.getParams();
        if (params == null) {
            params = Params.create();
        }
        return dao.accountCount(params);
    }

    public HashMap manualMoney(Page<Consume> page) {
        Params params = page.getParams();
        String tradeStates = null;
        String str = "0.00";
        if (params == null) {
            params = Params.create();
        } else {
            try {
                tradeStates = params.getString("tradeState");
            } catch (DtoException d) {
                tradeStates = null;
            }
        }
        params.put("tradeState", 2);
        Double manualMoney = dao.sumMoney(params);
        if (manualMoney != null) {
            str = new DecimalFormat("0.00").format(manualMoney);
        }
        int manualCount = dao.count(params);
        if (tradeStates != null) {
            params.put("tradeState", tradeStates);
        } else {
            params.remove("tradeState");
        }
        HashMap map = new HashMap();
        map.put("manualMoney", str);
        map.put("manualCount", manualCount);

        return map;
    }

    public Double sumMoneyByStatusAndDate(int status, int currentYear, int currentMonth, int currentDay) {
        Params params = Params.create("status", status);
        params.put("currentYear", currentYear);
        params.put("currentMonth", currentMonth);
        params.put("currentDay", currentDay);
        return dao.sumMoney(params);
    }

    /**
     * 根据状态和年月日统计交易笔数
     *
     * @param status
     * @param currentYear
     * @param currentMonth
     * @param currentDay
     * @return
     */
    public int countByStatusAndDate(int status, int currentYear, int currentMonth, int currentDay) {
        Params params = Params.create("status", status);
        params.put("currentYear", currentYear);
        params.put("currentMonth", currentMonth);
        params.put("currentDay", currentDay);
        return dao.count(params);
    }

    /**
     * 更改订单状态
     *
     * @param id
     * @param status
     * @return
     */
    public int updateStatus(String id, int status, BigDecimal amount) {
        Params params = Params.create("id", id);
        params.put("status", status);
        params.put("amount", amount);
        try {
            params.put("nowDate", DateUtil.YMdhms.parse(DateUtil.YMdhms.format(Utils.now())));
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
        return dao.updateStatus(params);
    }

    public List<Consume> selFind(Map<String, Object> map) {
        return dao.selFind(map);
    }

    public int selFindCount(String id) {
        return dao.selFindCount(id);
    }


    /**
     * 修改回调参数
     *
     * @param id
     * @param content
     * @return
     */
    public int updateTrade(String id, String content, int status) {
        Params params = Params.create("id", id);
        params.put("content", content);
        params.put("status", status);
        try {
            params.put("nowDate", DateUtil.YMdhms.parse(DateUtil.YMdhms.format(Utils.now())));
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
        return dao.updateTrade(params);
    }


    public List<Consume> findByDerived(Params params) {
        //  Params params = Params.create();
//    //        params.putAll(map);
//            Object merchantId = map.get("merchantId");
//            System.out.println(merchantId.toString());
//            params.put("merchantId",map.get("merchantId").toString());
//            System.out.println(params);
        return dao.findList(params);
    }

    public String findAdminChannel(String id) {
        return dao.findAdminChannel(id);
    }

    public Page<Consume> findListCount(Page<Consume> page) {
        return dao.findListCount(page);
    }

    public JobTrade findJobTradeById(String id) {
        return dao.findJobTradeById(id);
    }

}
