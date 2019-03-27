package co.b4pay.admin.common.core.enums;

/**
 * 鉴权类型
 *
 * @author YK
 */
public enum PermissionType implements BaseEnum<PermissionType, String> {

    /**
     * 可选
     */
    OPTIONAL("OPTIONAL", "可选"),
    /**
     * 必须
     */
    REQUIRED("REQUIRED", "必须");

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
    private PermissionType(final String code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

    /*
     * (non-Javadoc)
     * @see com.hola.common.enums.BaseEnum#getCode()
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /*
     * (non-Javadoc)
     * @see com.hola.common.enums.BaseEnum#getDesc()
     */
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
    public PermissionType getByCode(final String code) {
        if (code == null || code.isEmpty()) {
            return null;
        }
        final PermissionType[] enumValues = values();
        for (final PermissionType enumValue : enumValues) {
            if (enumValue.getCode().equals(code)) {
                return enumValue;
            }
        }
        return null;
    }

}
