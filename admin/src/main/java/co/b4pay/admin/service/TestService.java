package co.b4pay.admin.service;

import co.b4pay.admin.dao.PayrollDao;
import co.b4pay.admin.entity.Payroll;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;


/**
 * Created by john on 2018/4/23.
 */
@Service
@Transactional(readOnly = true)
public class TestService {

    /*@Reference
    private AccountServiceFacade accountServiceFacade;
    @Reference
    private MerchantServiceFacade merchantServiceFacade;*/

    /*public Account get() {
        return accountServiceFacade.get(1L);
    }*/

    /*public List<Merchant> getList() {

        return merchantServiceFacade.findList(0,10, null);
    }*/

}
