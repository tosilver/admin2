package co.b4pay.admin.common.system.dao;

import co.b4pay.admin.common.system.entity.Organization;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 组织机构Dao
 *
 * @author cc
 */
@MyBatisDao
public interface OrganizationDao extends ICrudDao<Organization> {

    /**
     * 移动1
     *
     * @param targetId
     * @param targetGetParentIds
     * @param sourceId
     */
    public void move1(@Param("targetId") String targetId, @Param("targetGetParentIds") String targetGetParentIds, @Param("sourceId") String sourceId);

    /**
     * 移动2
     *
     * @param targetMakeSelfAsParentIds
     * @param sourceMakeSelfAsParentIds
     * @param sourceId
     */
    public void move2(@Param("targetMakeSelfAsParentIds") String targetMakeSelfAsParentIds, @Param("sourceMakeSelfAsParentIds") String sourceMakeSelfAsParentIds, @Param("sourceId") String sourceId);

    public List<Organization> findAllWithExclude(Organization excludeOraganization);

}
