package co.b4pay.admin.dao;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.MallAddress;
import co.b4pay.admin.entity.MallTestTrade;
import co.b4pay.admin.entity.Withdraw;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;


/**
 * 消费记录DAO
 *
 * @author YK
 * @version $Id: ConsumeDao.java, v 0.1 2018年4月21日 上午11:41:12 YK Exp $
 */
@MyBatisDao
public interface MallTestTradeDao extends ICrudDao<MallTestTrade> {

    int updateStatus(Params params);

    Page<Withdraw> findListCount(Page<Withdraw> page);


}