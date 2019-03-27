package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;


/**
 * 省份信息
 * Created by john on 2017/8/1.
 */
public class Province extends BaseEntity {
    /**
     *
     */
    private static final long serialVersionUID = -6961214556266931481L;
    private String provinceId;
    private String province;

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
