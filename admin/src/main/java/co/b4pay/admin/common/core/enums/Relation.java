/**
 *
 */
package co.b4pay.admin.common.core.enums;

/**
 * 鉴权逻辑关系枚举
 *
 * @author YK
 * @version $Id: Relation.java, v 0.1 2013年5月12日 下午3:56:18 YK Exp $
 */
public enum Relation implements BaseEnum<Relation, String> {

    /**
     * 或的关系
     */
    OR("OR", "或"),

    /**
     * 与的关系
     */
    AND("AND", "与"),;

    private String code;

    private String desc;

    /**
     * @param code
     * @param desc
     */
    private Relation(final String code, final String desc) {
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
    public static Relation getByCode(final String code) {
        if (code == null || code.isEmpty()) {
            return null;
        }
        final Relation[] enumValues = values();
        for (final Relation enumValue : enumValues) {
            if (enumValue.getCode().equals(code)) {
                return enumValue;
            }
        }
        return null;
    }

}
