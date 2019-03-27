package co.b4pay.admin.dao;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.Province;

import java.util.List;

/**
 * 省份Dao
 * Created by john on 2017/8/2.
 */
@MyBatisDao
public interface ProvinceDao extends ICrudDao<Province> {
    /**
     * 根据省份名称查找省份信息
     *
     * @param province
     * @return
     */
    List<Province> getByProvince(String province);
}
