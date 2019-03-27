package co.b4pay.admin.service;

import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.dao.PartnerDao;
import co.b4pay.admin.entity.Partner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 合作商 Service
 * Created by john on 2018/4/27.
 */
@Service
@Transactional
public class PartnerService extends CrudService<PartnerDao, Partner> {

}
