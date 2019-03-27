package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;


/**
 * 城市
 * Created by john on 2017/8/2.
 */
public class City extends BaseEntity {
    /**
     *
     */
    private static final long serialVersionUID = -9187539821491723390L;
    private String cityId;
    private String city;
    private String provinceId;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
}
