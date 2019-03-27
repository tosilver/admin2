package co.b4pay.admin.common.system.dao;

import co.b4pay.admin.common.system.entity.Role;
import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;

/**
 * 权限Dao接口
 *
 * @author cc
 */
@MyBatisDao
public interface RoleDao extends ICrudDao<Role> {

}
