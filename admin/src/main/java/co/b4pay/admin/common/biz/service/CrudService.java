package co.b4pay.admin.common.biz.service;

import co.b4pay.admin.entity.base.BaseEntity;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.common.biz.dao.ICrudDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service基类
 *
 * @author YK
 * @version $Id: CrudService.java, v 0.1 2016年3月21日 下午5:59:00 YK Exp $
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends ICrudDao<T>, T extends BaseEntity> implements BaseService {

    /**
     * 持久层对象
     */
    @Autowired
    protected D dao;

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public T get(String id) {
        return dao.get(id);
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    public List<T> findList() {
        return dao.findList(Params.empty());
    }

    public List<T> findListCount() {
        return dao.findListCount(Params.empty());
    }

    /**
     * 获取符合条件数据条数
     *
     * @param entity
     * @return
     */
    public int count(T entity) {
        return dao.count(entity == null ? Params.empty() : Params.create(entity));
    }

    /**
     * 查询列表数据
     *
     * @param entity
     * @return
     */
    public List<T> findList(T entity) {
        return dao.findList(entity == null ? Params.empty() : Params.create(entity));
    }

    /**
     * 查询列表数据
     *
     * @param entity
     * @param pageSize
     * @return
     */
    public List<T> findList(T entity, int pageSize) {
        Params params = Params.create(entity, pageSize);
        return dao.findList(params);
    }

    /**
     * 查询列表数据
     *
     * @param entity
     * @return
     */
    public List<T> findList(T entity, int pageIndex, int pageSize) {
        Params params = Params.create(entity);
        int totalCount = dao.count(params);
        params = Params.create(params, pageIndex, pageSize, totalCount);
        return dao.findList(params);
    }

    /**
     * 查询列表数据
     *
     * @param entity
     * @return
     */
    public List<T> findList(T entity, int pageIndex, int pageSize, String orderBy) {
        Params params = Params.create(entity);
        int totalCount = dao.count(params);
        params = Params.create(params, pageIndex, pageSize, totalCount, orderBy);
        return dao.findList(params);
    }

    /**
     * 查询分页数据
     *
     * @param page
     * @return
     */
    public Page<T> findPage(Page<T> page) {
        Params params = page.getParams();

        if (params == null) {
            params = Params.create();
        }
        int totalCount = dao.count(params);
        if (totalCount < 1) {
            return page;
        }
        page.init(totalCount);
        int pageIndex = page.getPageIndex();
        int totalPage = page.getTotalPage();
        if (totalPage < pageIndex) {
            page.setPageIndex(1);
            page.setPageFirst(0);
        }
        params.initPage(page);
//        int a = dao.findListCount(params).size();
//        System.out.println("数据"+a);
        page.setList(dao.findList(params));
        return page;
    }

    /**
     * 保存数据
     *
     * @param entity
     */
    @Transactional
    public int save(T entity) {
        entity.preInsert();
        return dao.insert(entity);
    }

    /**
     * 更新数据
     *
     * @param entity
     */
    @Transactional
    public int update(T entity) {
        entity.preUpdate();
        return dao.update(entity);
    }


    /**
     * 保存或更新数据
     *
     * @param entity
     */
    @Deprecated
    @Transactional
    public int saveOrUpdate(T entity) {
        if (entity.getId() == null) {
            return save(entity);
        } else {
            return update(entity);
        }
    }

    /**
     * 删除数据
     *
     * @param id
     */
    @Transactional
    public int delete(String id) {
        return dao.delete(id);
    }

}