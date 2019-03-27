package co.b4pay.admin.dao;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.TUser;
import co.b4pay.admin.entity.base.Params;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 分账用户信息
 *
 * @author Tosdom
 */
@MyBatisDao
public interface TUserDao extends ICrudDao<TUser> {
    int updateStatus(Params params);

    List<TUser> findList();
}
