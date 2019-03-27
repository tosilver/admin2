package co.b4pay.admin.dao;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.Settlement;

/**
 * 商户结算信息
 * Created by john on 2018/6/7.
 */
@MyBatisDao
public interface SettlementDao extends ICrudDao<Settlement> {
    // 根据商户ID查找信息
    int findByMerchantId(String merchantId);
}
