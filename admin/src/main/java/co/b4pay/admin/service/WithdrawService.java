package co.b4pay.admin.service;

import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.common.util.DateUtil;
import co.b4pay.admin.controller.Utils.Utils;
import co.b4pay.admin.dao.ConsumeDao;
import co.b4pay.admin.dao.MerchantDao;
import co.b4pay.admin.dao.WithdrawDao;
import co.b4pay.admin.entity.Consume;
import co.b4pay.admin.entity.JobTrade;
import co.b4pay.admin.entity.Withdraw;
import co.b4pay.admin.entity.base.DtoException;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 申请审批记录Service
 *
 * @author YK
 * @version $Id: ConsumeService.java, v 0.1 2018年4月21日 上午11:45:58 YK Exp $
 */
@Service
@Transactional
public class WithdrawService extends CrudService<WithdrawDao, Withdraw> {
    @Autowired
    private WithdrawDao withdrawDao;

    public Double sumMoney(Page<Withdraw> page) {
        Params params = page.getParams();
        if (params == null) {
            params = Params.create();
        }
        return dao.sumMoney(params);
    }

    public Double getSuccessRate(Page<Withdraw> page) {
        Params params = page.getParams();
        if (params == null) {
            params = Params.create();
        }
        return dao.getSuccessRate(params);
    }


    public Integer accountCount(Page<Withdraw> page) {
        Params params = page.getParams();
        if (params == null) {
            params = Params.create();
        }
        return dao.accountCount(params);
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
            System.out.println(e.getMessage());
        }
        return dao.updateTrade(params);
    }


    public List<Withdraw> findByDerived(Params params) {
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

    public Page<Withdraw> findListCount(Page<Withdraw> page) {
        return dao.findListCount(page);
    }

}
