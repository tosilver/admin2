package co.b4pay.admin.service;

import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.controller.Utils.Utils;
import co.b4pay.admin.dao.OrderDao;
import co.b4pay.admin.entity.Consume;
import co.b4pay.admin.entity.Order;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


/**
 * 订单记录Service
 *
 * @author 曾关培
 * @version $Id: ConsumeService.java, v 0.1 2018年4月21日 上午11:45:58 YK Exp $
 */
@Service
@Transactional
public class OrderService extends CrudService<OrderDao, Order> {
    @Autowired
    private OrderDao orderDao;


    public List<Order> findList(Params params) {
        return dao.findList(params);
    }

    public Double getSuccessRate(Page<Order> page) {
        Params params = page.getParams();
        if (params == null) {
            params = Params.create();
        }
        return dao.getSuccessRate(params);
    }

    public String findAdminChannel(String id) {
        return dao.findAdminChannel(id);
    }

    public Double sumMoney(Page<Order> page) {
        Params params = page.getParams();
        if (params == null) {
            params = Params.create();
        }
        return dao.sumMoney(params);
    }

    public Integer count(Page<Order> page) {
        Params params = page.getParams();
        if (params == null) {
            params = Params.create();
        }
        return dao.count(params);
    }

    /**
     * 统计已支付金额
     *
     * @param page
     * @return
     */
    public Double sumAccountMoney(Page<Order> page) {
        Params params = page.getParams();
        if (params == null) {
            params = Params.create();
        }
        return dao.sumAccountMoney(params);
    }

    /**
     * 统计已支付个数
     *
     * @param page
     * @return
     */
    public Integer sumAccountCount(Page<Order> page) {
        Params params = page.getParams();
        if (params == null) {
            params = Params.create();
        }
        return dao.sumAccountCount(params);
    }

    /**
     * 更改支付状态
     */
    public int updateStatus(String id, int status, Double payedMoney) {
        Params params = Params.create("orderid", id);
        params.put("status", status);
        BigDecimal bigDecimalAmount = Utils.getBigDecimal(payedMoney);
        params.put("payedMoney", bigDecimalAmount);
        return dao.updateStatus(params);
    }

}
