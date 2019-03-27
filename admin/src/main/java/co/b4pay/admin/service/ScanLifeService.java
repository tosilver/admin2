package co.b4pay.admin.service;

import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.dao.ScanLifeDao;
import co.b4pay.admin.entity.ScanLife;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ScanLifeService extends CrudService<ScanLifeDao, ScanLife> {
}
