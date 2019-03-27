package co.b4pay.admin.service;

import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.dao.MerchantDao;
import co.b4pay.admin.dao.MerchantRateDao;
import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.entity.Merchant;
import co.b4pay.admin.entity.MerchantRate;
import co.b4pay.admin.entity.base.Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Created by john on 2018/6/7.
 */
@Service
@Transactional(readOnly = true)
public class MerchantRateService extends CrudService<MerchantRateDao, MerchantRate> {

    @Autowired
    private MerchantDao merchantDao;

    @Override
    @Transactional
    public int save(MerchantRate merchantRate) {
        Merchant merchant = merchantDao.get(merchantRate.getMerchant().getId());
        if (StringUtil.isBlank(merchant.getSecretKey())) {
            merchant.setSecretKey(UUID.randomUUID().toString().replace("-", ""));
            merchantDao.update(merchant);
        }
        return super.save(merchantRate);
    }

    public List<MerchantRate> findByMerchantId(String merchantId) {
        return dao.findByMerchantId(merchantId);
    }

    //根据商户ID和产品ID验证商户费率是否存在
    public int ifExists(String merchantId, String routerId) {
        Params params = Params.create("merchantId", merchantId);
        params.put("routerId", routerId);
        return dao.ifExists(params);
    }
}
