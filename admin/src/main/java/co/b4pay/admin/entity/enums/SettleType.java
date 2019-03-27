package co.b4pay.admin.entity.enums;

/**
 * Created by john on 2018/6/7.
 */
public enum SettleType {
    D0("D0"),
    D1("D1");

    private String text;

    private SettleType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
