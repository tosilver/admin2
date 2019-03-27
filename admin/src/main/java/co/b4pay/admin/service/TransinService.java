package co.b4pay.admin.service;

import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.dao.TransinDao;
import co.b4pay.admin.entity.Transin;
import co.b4pay.admin.entity.base.Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransinService extends CrudService<TransinDao, Transin> {

    @Autowired
    TransinDao transinDao;

    public int updateStatus(String id, int status) {
        Params params = Params.create("id", id);
        params.put("status", status);
        return dao.updateStatus(params);
    }
}
