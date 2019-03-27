package co.b4pay.admin.service;

import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.dao.CallRecordDao;
import co.b4pay.admin.entity.CallRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CallRecordService extends CrudService<CallRecordDao, CallRecord> {
}
