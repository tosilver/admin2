package co.b4pay.admin.dao;


import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.YfbPayroll;

@MyBatisDao
public interface kjPayrollDao extends ICrudDao<YfbPayroll> {
    YfbPayroll findByBatchNo(String batchNo);
}
