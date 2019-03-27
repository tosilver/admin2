package co.b4pay.admin.service;

import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.common.util.DateUtil;
import co.b4pay.admin.controller.Utils.Utils;
import co.b4pay.admin.dao.MallAddressDao;
import co.b4pay.admin.dao.MallTestTradeDao;
import co.b4pay.admin.entity.MallAddress;
import co.b4pay.admin.entity.MallTestTrade;
import co.b4pay.admin.entity.Withdraw;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 申请审批记录Service
 *
 * @author YK
 * @version $Id: ConsumeService.java, v 0.1 2018年4月21日 上午11:45:58 YK Exp $
 */
@Service
@Transactional
public class MallTestTradeService extends CrudService<MallTestTradeDao, MallTestTrade> {
    @Autowired
    private MallTestTradeDao mallTestTradeDao;


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
    public int updateStatus(String id, int status) {
        Params params = Params.create("id", id);
        params.put("status", status);
        try {
            params.put("nowDate", DateUtil.YMdhms.parse(DateUtil.YMdhms.format(Utils.now())));
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
        return dao.updateStatus(params);
    }


    public Page<Withdraw> findListCount(Page<Withdraw> page) {
        return dao.findListCount(page);
    }

}
