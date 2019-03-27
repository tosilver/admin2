package co.b4pay.admin.dao;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.MerchantRate;
import co.b4pay.admin.entity.base.Params;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 商户费率
 * Created by john on 2018/6/7.
 */
@MyBatisDao
public interface MerchantRateDao extends ICrudDao<MerchantRate> {
    // 根据商户ID查找其下所有产品的费率信息
    List<MerchantRate> findByMerchantId(String merchantId);

    //根据商户ID和产品ID验证商户费率是否存在
    int ifExists(Params params);

    MerchantRate findByPayCost(@Param("merchantId") String merchantId, @Param("routerId") String routerId);
}
