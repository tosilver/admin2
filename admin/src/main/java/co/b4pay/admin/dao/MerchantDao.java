package co.b4pay.admin.dao;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.Merchant;
import co.b4pay.admin.entity.base.Params;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商户DAO
 *
 * @author YK
 * @version $Id: MerchantDao.java, v 0.1 2018年4月20日 下午14:02:12 YK Exp $
 */
@MyBatisDao
public interface MerchantDao extends ICrudDao<Merchant> {
    /**
     * 更改商户状态
     *
     * @param params
     * @return
     */
    int updateStatus(Params params);

    //根据公司名查找商户信息
    int findByCompany(String compay);

    BigDecimal findByBalance(String id);

    Merchant findAll(String id);

    List<Merchant> findByAdminMerchant(String id);

}