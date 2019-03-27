package co.b4pay.admin.common.biz.dao;

import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.base.BaseEntity;
import co.b4pay.admin.entity.base.Params;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * DAO支持类实现
 *
 * @param <T>
 * @author cc
 */
@MyBatisDao
@Repository
public interface ICrudDao<T extends BaseEntity> extends IBaseDao {

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    T get(Serializable id);

    /**
     * 获取符合条件数据条数
     *
     * @param params
     * @return
     */
    int count(Params params);

    List<T> findListCount(Params params);

    /**
     * 获取符合条件数据条数
     *
     * @param entity
     * @return
     */
    //int count(T entity);

    /**
     * 查询数据列表
     *
     * @param params
     * @return
     */
    List<T> findList(Params params);

    // /**
    // * 查询总数
    // *
    // * @return
    // */
    // public int myCount(@Param("page") Page<T> page);

    // /**
    // * 查询分页数据
    // *
    // * @param page
    // * @return
    // */
    // public List<T> findPage(@Param("page") Page<T> page);

    /**
     * 插入数据
     *
     * @param entity
     * @return
     */
    int insert(T entity);

    /**
     * 更新数据
     *
     * @param entity
     * @return
     */
    int update(T entity);

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    int delete(Serializable id);
}