package co.b4pay.admin.dao;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.Channel;
import co.b4pay.admin.entity.Transin;
import co.b4pay.admin.entity.base.Params;

/**
 * 分账用户信息
 *
 * @author Tosdom
 */
@MyBatisDao
public interface TransinDao extends ICrudDao<Transin> {
    int updateStatus(Params params);
}
