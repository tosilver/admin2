package co.b4pay.admin.service;

import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.dao.TUserDao;
import co.b4pay.admin.dao.TransinDao;
import co.b4pay.admin.entity.TUser;
import co.b4pay.admin.entity.Transin;
import co.b4pay.admin.entity.base.Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TUserService extends CrudService<TUserDao, TUser> {

    @Autowired
    TUserDao tUserDao;

    public int updateStatus(String id, int status) {
        Params params = Params.create("account", id);
        params.put("status", status);
        return dao.updateStatus(params);
    }
}
