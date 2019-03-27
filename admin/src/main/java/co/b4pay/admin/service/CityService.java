package co.b4pay.admin.service;

import co.b4pay.admin.dao.CityDao;

import co.b4pay.admin.entity.City;
import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.entity.base.Params;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 城市Service
 * Created by john on 2017/8/2.
 */
@Service
@Transactional(readOnly = true)
public class CityService extends CrudService<CityDao, City> {
    /**
     * 根据省份ID查找城市信息
     *
     * @param provinceId
     * @return
     */
    public List<City> getByProvinceId(String provinceId) {
        return dao.getByProvinceId(provinceId);
    }

    /**
     * 根据省份ID和城市名称查找城市信息
     *
     * @param provinceId
     * @param city
     * @return
     */
    public List<City> getByCity(String provinceId, String city) {
        Params params = Params.create("provinceId", provinceId);
        params.put("city", city);
        return dao.getByCity(params);
    }
}
