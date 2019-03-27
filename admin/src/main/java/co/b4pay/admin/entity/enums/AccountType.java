package co.b4pay.admin.entity.enums;


/**
 * 账户类型枚举类
 * Created by john on 2018/6/7.
 */
public enum AccountType {
    PBLIIC_ACCOUNT("对公账户"),
    PRIVATE_ACCOUNT("私账");

    private String text;

    private AccountType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
