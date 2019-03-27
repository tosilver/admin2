package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;


/**
 * 合作商信息
 * Created by john on 2018/4/27.
 */
public class Partner extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 438685535430355797L;
    private String company;     //公司名
    private String address;     //地址
    private String contacts;    //联系人
    private String tel;         //联系电话

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
