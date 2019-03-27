package co.b4pay.admin.service;

import co.b4pay.admin.dao.SettlementDao;
import co.b4pay.admin.common.biz.service.CrudService;

import co.b4pay.admin.entity.Settlement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商户结算信息
 * Created by john on 2018/6/7.
 */
@Service
@Transactional(readOnly = true)
public class SettlementService extends CrudService<SettlementDao, Settlement> {
    // 根据商户ID查找信息
    public int findByMerchantId(String merchantId) {
        return dao.findByMerchantId(merchantId);
    }
}
