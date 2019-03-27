package co.b4pay.admin.controller.Utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class Utils {

    public static BigDecimal getBigDecimal(Object value) {

        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        }
        if (ret != null) {
            return ret.setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            return BigDecimal.ZERO;
        }
    }


    /**
     * 获取当前时间
     *
     * @return Long
     */
    public static Date now() {
        return Date.from(LocalDateTime.now().toInstant(ZoneOffset.of("+8")));
    }

    /**
     * 获取当前时间毫秒级
     *
     * @return Long
     */
    public static Long nowTimeMilliSecond() {
        return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 获取当前时间秒级
     *
     * @return Long
     */
    public static Long nowTimeSecond() {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
    }
}
