package co.b4pay.admin.dao;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.MerchantFunction;

/**
 * 商户接口DAO
 *
 * @author YK
 * @version $Id: MerchantFunctionDao.java, v 0.1 2018年4月21日 下午18:35:12 YK Exp $
 */
@MyBatisDao
public interface MerchantFunctionDao extends ICrudDao<MerchantFunction> {

    int existsMerchantFunction(MerchantFunction merchantFunction);

}