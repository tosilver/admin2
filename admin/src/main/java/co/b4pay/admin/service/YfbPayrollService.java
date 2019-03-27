package co.b4pay.admin.service;

import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.dao.DetailDataCardDao;
import co.b4pay.admin.entity.DetailDataCard;


import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class YfbPayrollService extends CrudService<DetailDataCardDao, DetailDataCard> {

    public Double sumAmount(Page<DetailDataCard> page) {
        Params params = page.getParams();
        if (params == null) {
            params = Params.create();
        }
        return dao.sumAmount(params);
    }

    public Double sumCost(Page<DetailDataCard> page) {
        Params params = page.getParams();
        if (params == null) {
            params = Params.create();
        }
        return dao.sumCost(params);
    }

    public Double sumAmountAndCost(Page<DetailDataCard> page) {
        Params params = page.getParams();
        if (params == null) {
            params = Params.create();
        }
        return dao.sumAmountAndCost(params);
    }

}
