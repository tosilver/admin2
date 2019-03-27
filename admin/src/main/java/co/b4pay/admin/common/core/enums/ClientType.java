package co.b4pay.admin.common.core.enums;

/**
 * 客户端类型
 *
 * @author YK
 * @version $Id: ClientType.java, v 0.1 2013-5-5 上午10:55:41 YK Exp $
 */
public enum ClientType implements BaseEnum<ClientType, String> {

    /**
     * 电脑端网站
     */
    PCWEB("PCWEB", "电脑端网站"),

    /**
     * 手机客户端
     */
    MOBILE("MOBILE", "手机客户端");

    /**
     * 枚举码
     */
    private String code;

    /**
     * 枚举描述
     */
    private String desc;

    /**
     * @param code
     * @param desc
     */
    private ClientType(final String code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    /**
     * 根据枚举码获取枚举对象
     *
     * @param code 枚举码
     * @return 枚举对象
     */
    public ClientType getByCode(final String code) {
        if (code == null || code.isEmpty()) {
            return null;
        }
        final ClientType[] enumValues = values();
        for (final ClientType enumValue : enumValues) {
            if (enumValue.getCode().equals(code)) {
                return enumValue;
            }
        }
        return null;
    }

}
