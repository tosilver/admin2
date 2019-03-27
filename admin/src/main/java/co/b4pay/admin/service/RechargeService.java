package co.b4pay.admin.service;

import co.b4pay.admin.dao.MerchantDao;
import co.b4pay.admin.dao.RechargeDao;
import co.b4pay.admin.common.biz.service.CrudService;

import co.b4pay.admin.entity.Merchant;
import co.b4pay.admin.entity.Recharge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 充值记录Service
 *
 * @author YK
 * @version $Id: RechargeService.java, v 0.1 2018年4月21日 上午11:33:58 YK Exp $
 */
@Service
@Transactional
public class RechargeService extends CrudService<RechargeDao, Recharge> {

    @Autowired
    private MerchantDao merchantDao;

    @Override
    public int save(Recharge recharge) {
        dao.insert(recharge);
        Merchant merchant = merchantDao.get(recharge.getMerchant().getId());
        return merchantDao.update(merchant);
    }
}
