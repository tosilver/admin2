package co.b4pay.admin.common.core.config;

import co.b4pay.admin.common.util.PropertiesLoader;
import co.b4pay.admin.common.core.kit.PropKit;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 全局配置
 *
 * @author YK
 */
public final class MainConfig {
    /**
     * 保存全局属性值
     */
    private static final Map<String, String> map = Maps.newConcurrentMap();

    /**
     * 属性文件加载对象
     */
    private static final PropertiesLoader loader = new PropertiesLoader("config.properties");

    /**
     * 获取配置
     *
     * @see ${fns:getConfig('adminPath')}
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null) {
            value = loader.getProperty(key);
            map.put(key, value != null ? value : StringUtils.EMPTY);
        }
        return value;
    }

    public static final String CURRENT_USER = "user";
    public static final String SESSION_FORCE_LOGOUT_KEY = "session.force.logout";
    public static final String MESSAGE = "message";
    public static final String PARAM_DIGEST = "digest";
    public static final String PARAM_USERNAME = "username";

    /**
     * 开发模式
     */
    public static final Boolean isDevMode = PropKit.getBoolean("devMode");
}