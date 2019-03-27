package co.b4pay.admin.dao;


import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.Order;
import co.b4pay.admin.entity.base.Params;

/**
 * 订单记录dao
 */
@MyBatisDao
public interface OrderDao extends ICrudDao<Order> {

    Double getSuccessRate(Params params);

    String findAdminChannel(String id);

    //查询总金额
    Double sumMoney(Params params);

    //查询已付总金额
    Double sumAccountMoney(Params params);

    //统计已支付个数
    Integer sumAccountCount(Params params);

    //更改支付状态
    int updateStatus(Params params);
}
