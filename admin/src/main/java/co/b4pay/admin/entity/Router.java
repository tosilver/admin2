package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;

/**
 * 路由
 * Created by john on 2018/6/13.
 */
public class Router extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1224788125223290247L;
    private String name;           //路由
    private String version;        //版本

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
