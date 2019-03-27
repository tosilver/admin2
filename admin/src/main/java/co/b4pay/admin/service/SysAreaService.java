package co.b4pay.admin.service;

import co.b4pay.admin.dao.SysAreaDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.common.biz.service.CrudService;

import co.b4pay.admin.entity.SysArea;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 地区Service
 * Created by john on 2017/8/2.
 */
@MyBatisDao
@Transactional(readOnly = true)
public class SysAreaService extends CrudService<SysAreaDao, SysArea> {
    public List<SysArea> getByCityId(String cityId) {
        return dao.getByCityId(cityId);
    }
}
