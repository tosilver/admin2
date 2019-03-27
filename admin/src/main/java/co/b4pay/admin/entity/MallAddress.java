package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;

import java.math.BigDecimal;

/****
 * 商城地址
 * @author Tosdom
 */
public class MallAddress extends BaseEntity {


    private String mallName;            //商城名称
    private String mallAdmin;            //商城名称
    private String address;            //商城地址
    private BigDecimal turnover;            //商城地址

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMallAdmin() {
        return mallAdmin;
    }

    public void setMallAdmin(String mallAdmin) {
        this.mallAdmin = mallAdmin;
    }

    public BigDecimal getTurnover() {
        return turnover;
    }

    public void setTurnover(BigDecimal turnover) {
        this.turnover = turnover;
    }
}
