package co.b4pay.admin.dao;

import co.b4pay.admin.entity.City;
import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.base.Params;

import java.util.List;

/**
 * 城市Dao
 * Created by john on 2017/8/2.
 */
@MyBatisDao
public interface CityDao extends ICrudDao<City> {
    /**
     * 根据省份ID查找城市信息
     *
     * @param provinceId
     * @return
     */
    List<City> getByProvinceId(String provinceId);

    /**
     * 根据省份ID和城市名称查找城市信息
     *
     * @param params
     * @return
     */
    public List<City> getByCity(Params params);
}
