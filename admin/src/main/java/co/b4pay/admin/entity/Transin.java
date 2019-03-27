package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;

public class Transin extends BaseEntity {
    private String pid;
    private String realname;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
}
