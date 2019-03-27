/**
 * Hola.YK Inc.
 * Copyright (c) 2012-2013 All Rights Reserved.
 */
package co.b4pay.admin.common.util;

/**
 * 数字处理工具
 *
 * @author YK
 * @version $Id: NumberUtil.java, v 0.1 2016年8月17日 上午1:20:20 YK Exp $
 */
public class NumberUtil {

    public static final boolean equals(Number a, Number b) {
        return a != null && a.equals(b);
    }

    public static final boolean notEquals(Number a, Number b) {
        return equals(a, b) == false;
    }
}