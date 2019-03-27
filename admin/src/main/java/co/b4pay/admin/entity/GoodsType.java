package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;

/**
 * 商品类型
 * Created by john on 2018/6/13.
 */
public class GoodsType extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1224788125223299247L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
