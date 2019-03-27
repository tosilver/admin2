package co.b4pay.admin.service;

import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.dao.MerchantPayDao;
import co.b4pay.admin.entity.MerchantPay;

import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MerchantPayService extends CrudService<MerchantPayDao, MerchantPay> {
    public Double sumAmount(Page<MerchantPay> page) {
        Params params = page.getParams();
        if (params == null) {
            params = Params.create();
        }
        return dao.sumAmount(params);
    }

    public int updateStatus(String id, int status) {
        Params params = Params.create("id", id);
        params.put("status", status);
        return dao.updateStatus(params);
    }
}
