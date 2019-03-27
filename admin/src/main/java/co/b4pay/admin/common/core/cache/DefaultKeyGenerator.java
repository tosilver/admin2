package co.b4pay.admin.common.core.cache;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 重构默认的缓存KEY生成器
 *
 * @author YK
 * @version $Id v 0.1 2016年12月06日 10:52 Exp $
 */
public class DefaultKeyGenerator implements KeyGenerator {
    private static final String NULL_STR = "null";

    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuffer key = new StringBuffer();
        key.append(target.getClass().getCanonicalName());
        key.append(".");
        key.append(method.getName());
        key.append("(");
        if (params != null) {
            int length = params.length;
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    key.append(", ");
                }
                Object param = params[i];
                if (param == null) {
                    key.append(NULL_STR);
                } else if (param.getClass().isPrimitive()) {
                    key.append(param);
                } else if (param.getClass().isArray()) {
                    key.append(Arrays.deepToString((Object[]) param));
                    //} else if (param.getClass().isAssignableFrom(Collection.class)) {
                } else {
                    key.append(param);
                }
            }
        }
        key.append(")");

        return key.toString();
    }
}