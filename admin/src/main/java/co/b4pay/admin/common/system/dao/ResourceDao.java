package co.b4pay.admin.common.system.dao;


import co.b4pay.admin.common.system.entity.Resource;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;


/**
 * 资源Dao接口
 *
 * @author cc
 */
@MyBatisDao
public interface ResourceDao extends ICrudDao<Resource> {

}
