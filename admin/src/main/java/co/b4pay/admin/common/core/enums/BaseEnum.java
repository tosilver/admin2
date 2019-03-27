/**
 *
 */
package co.b4pay.admin.common.core.enums;

/**
 * 枚举基础接口
 * <p>
 * &nbsp;&nbsp;主要用于约束枚举，获取代码和备注，使实现本系统的枚举形成统一
 * <p>
 *
 * @param <T> EnumType
 * @param <E> CodeType
 * @author YK
 * @version $Id: BaseEnum.java, v 0.1 2013-07-30 PM15:00:36 YK Exp $
 */
public interface BaseEnum<T, E> {

    /**
     * 获得该枚举的描述
     *
     * @return 枚举描述
     */
    String getDesc();

    /**
     * 获得枚举码
     *
     * @return 枚举码
     */
    E getCode();

}
