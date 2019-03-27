package co.b4pay.admin.dao;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.DetailDataAccount;
import co.b4pay.admin.entity.DetailDataCard;
import co.b4pay.admin.entity.base.Params;


@MyBatisDao
public interface DetailDataCardDao extends ICrudDao<DetailDataCard> {

    DetailDataCard findBySerialNo(String serialNo);

    Double sumAmount(Params params);

    Double sumCost(Params params);

    Double sumAmountAndCost(Params params);

    /**
     * 根据批次号查询批付订单
     *
     * @param batchNo 批付流水id
     * @return 批付订单
     */
    DetailDataCard findByBatchNo(String batchNo);
}
