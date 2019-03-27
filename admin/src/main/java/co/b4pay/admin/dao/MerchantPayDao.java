package co.b4pay.admin.dao;


import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.MerchantPay;
import co.b4pay.admin.entity.base.Params;

@MyBatisDao
public interface MerchantPayDao extends ICrudDao<MerchantPay> {
    Double sumAmount(Params params);

    int updateStatus(Params params);
}
