package co.b4pay.admin.dao;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.SysArea;

import java.util.List;

/**
 * 地区DAO
 * Created by liuwei on 2017/8/2.
 */
@MyBatisDao
public interface SysAreaDao extends ICrudDao<SysArea> {
    List<SysArea> getByCityId(String cityId);
}
