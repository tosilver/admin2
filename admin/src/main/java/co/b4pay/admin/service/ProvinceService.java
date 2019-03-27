package co.b4pay.admin.service;

import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.dao.ProvinceDao;
import co.b4pay.admin.entity.Province;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 省份Service
 * Created by john on 2017/8/2.
 */
@Service
@Transactional(readOnly = true)
public class ProvinceService extends CrudService<ProvinceDao, Province> {
    /**
     * 根据省份名称查找省份信息
     *
     * @param province
     * @return
     */
    public List<Province> getByProvince(String province) {
        return dao.getByProvince(province);
    }
}
