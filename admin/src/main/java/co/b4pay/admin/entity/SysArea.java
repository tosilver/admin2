package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;

/**
 * 地区
 * Created by liuwei on 2017/8/2.
 */
public class SysArea extends BaseEntity {

    private static final long serialVersionUID = 6689146077189961154L;

    private String areaId;
    private String area;
    private String cityId;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
