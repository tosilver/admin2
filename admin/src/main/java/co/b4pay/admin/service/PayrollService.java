package co.b4pay.admin.service;


import java.util.List;

import co.b4pay.admin.entity.Consume;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.dao.PayrollDao;
import co.b4pay.admin.entity.Payroll;


@Service
@Transactional
public class PayrollService extends CrudService<PayrollDao, Payroll> {
    @Autowired
    PayrollDao payrollDao;

    public Payroll findByUpOrderId(String uporderId) {
        return payrollDao.findByUpOrderId(uporderId);

    }

    public List<Payroll> findByStatus(Integer status) {
        return payrollDao.findByStatus(status);

    }

    public Payroll findByDownOrderId(String downOrderId, String merchantId) {
        return payrollDao.findByDownOrderId(downOrderId, merchantId);
    }

    public Double sumAmount(Page<Payroll> page) {
        Params params = page.getParams();
        if (params == null) {
            params = Params.create();
        }
        return dao.sumAmount(params);
    }
}
