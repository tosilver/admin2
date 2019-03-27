package co.b4pay.admin.dao;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.Recharge;

/**
 * 充值记录DAO
 *
 * @author YK
 * @version $Id: RechargeDao.java, v 0.1 2018年4月21日 上午11:33:12 YK Exp $
 */
@MyBatisDao
public interface RechargeDao extends ICrudDao<Recharge> {

}