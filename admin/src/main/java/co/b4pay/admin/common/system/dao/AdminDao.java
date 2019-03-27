package co.b4pay.admin.common.system.dao;

import co.b4pay.admin.common.system.entity.Admin;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;

import org.apache.ibatis.annotations.Param;

/**
 * 用户Dao接口
 *
 * @author cc
 */
@MyBatisDao
public interface AdminDao extends ICrudDao<Admin> {

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    Admin getByUsername(@Param("username") String username);

    /**
     * 启用用户
     *
     * @param id
     */
    void startUsing(String id);

    Admin findByMerchantId(String merchantIds);

}
