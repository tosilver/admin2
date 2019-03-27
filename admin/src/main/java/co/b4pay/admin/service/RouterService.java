package co.b4pay.admin.service;

import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.dao.RouterDao;
import co.b4pay.admin.entity.Router;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 路由
 * Created by john on 2018/6/13.
 */
@Service
@Transactional(readOnly = true)
public class RouterService extends CrudService<RouterDao, Router> {
}
