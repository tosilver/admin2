package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;
import co.b4pay.admin.entity.enums.AccountType;
import co.b4pay.admin.entity.enums.SettleType;

/**
 * 结算信息
 * Created by john on 2018/6/7.
 */
public class Settlement extends BaseEntity {

    private static final long serialVersionUID = 2075919271856405646L;

    private Merchant merchant;                       //商户
    private SettleType settleType;                     //结算类型
    private AccountType accountType;                    //账户类型
    private String bankAccount;                    //银行账户
    private String bankName;                       //开户银行
    private String bankNickname;                   //银行名称

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public SettleType getSettleType() {
        return settleType;
    }

    public void setSettleType(SettleType settleType) {
        this.settleType = settleType;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNickname() {
        return bankNickname;
    }

    public void setBankNickname(String bankNickname) {
        this.bankNickname = bankNickname;
    }
}
