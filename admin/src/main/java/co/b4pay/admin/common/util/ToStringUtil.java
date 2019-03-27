/**
 * Hola.YK Inc.
 * Copyright (c) 2012-2013 All Rights Reserved.
 */
package co.b4pay.admin.common.util;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 对象格式化
 *
 * @author YK
 * @version $Id: ToStringUtil.java, v 0.1 2013年9月1日 下午8:14:06 YK Exp $
 */
public class ToStringUtil {

    public static final String toString(Object object) {
        return toString(object, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static final String toString(Object object, ToStringStyle toStringStyle) {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return object.toString();
        }
        return ToStringBuilder.reflectionToString(object, toStringStyle, true);
    }

    /**
     * The short prefix toString style. Using the <code>Person</code> example from {@link ToStringBuilder}, the output
     * would look like this:
     * <p>
     * <pre>
     * Person[name=John Doe,age=33,smoker=false]
     * </pre>
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE, true);
    }
}