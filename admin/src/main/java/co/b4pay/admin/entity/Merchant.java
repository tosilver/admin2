package co.b4pay.admin.entity;

import java.math.BigDecimal;

import co.b4pay.admin.entity.base.BaseEntity;


/**
 * 商户信息
 *
 * @author YK
 * @version $Id: Merchant.java, v 0.1 2018年4月20日 下午14:03:09 YK Exp $
 */
public class Merchant extends BaseEntity {
    /**
     * @return the balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3050115515232663085L;

    private String company;                  // 公司名
    private String contacts;                 // 联系人
    private String idCard;                   //身份证
    private String tel;                      // 联系电话
    private Province province;                 // 省份
    private City city;                     // 城市
    private SysArea area;                     // 地区
    private String address;                  // 公司地址

    private String secretKey;                //商户密钥
    private BigDecimal balance;                  //商戶总余额
    private BigDecimal accountBalance;            //入账余额
    private Settlement settlement;               //结算信息
    private BigDecimal accountFrozen;           //出账冻结金额
    private BigDecimal withdrawBalance;           //可提现余额
    private BigDecimal withdrawFrozen;           //提现冻结金额

    public Settlement getSettlement() {
        return settlement;
    }

    public void setSettlement(Settlement settlement) {
        this.settlement = settlement;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public SysArea getArea() {
        return area;
    }

    public void setArea(SysArea area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getAccountFrozen() {
        return accountFrozen;
    }

    public void setAccountFrozen(BigDecimal accountFrozen) {
        this.accountFrozen = accountFrozen;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public BigDecimal getWithdrawBalance() {
        return withdrawBalance;
    }

    public void setWithdrawBalance(BigDecimal withdrawBalance) {
        this.withdrawBalance = withdrawBalance;
    }

    public BigDecimal getWithdrawFrozen() {
        return withdrawFrozen;
    }

    public void setWithdrawFrozen(BigDecimal withdrawFrozen) {
        this.withdrawFrozen = withdrawFrozen;
    }
}
