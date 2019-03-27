package co.b4pay.admin.dao;

import java.util.List;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.Payroll;
import co.b4pay.admin.entity.base.Params;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface PayrollDao extends ICrudDao<Payroll> {

    Payroll findByUpOrderId(String upOrderId);

    Payroll findByDownOrderId(@Param("downOrderId") String downOrderId, @Param("merchantId") String merchantId);

    List<Payroll> findByStatus(Integer status);

    Double sumAmount(Params params);
}
