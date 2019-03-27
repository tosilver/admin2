package co.b4pay.admin.dao;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.DetailDataCard;
import co.b4pay.admin.entity.Xiafa;
import co.b4pay.admin.entity.base.Params;


/**
 * @author Tosdom
 */
@MyBatisDao
public interface XiafaDao extends ICrudDao<Xiafa> {
    int updateStatus(Params params);
}
