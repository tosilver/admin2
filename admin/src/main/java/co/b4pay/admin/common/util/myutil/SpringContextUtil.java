package co.b4pay.admin.common.util.myutil;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 获取spring容器工具类
 * 以访问容器中定义的其他bean
 * Created with IntelliJ IDEA
 * Created By AZain
 * Date: 2018-01-22
 * Time: 15:57
 */
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据bean名称获取bean实例
     *
     * @param name bean实例名
     * @param <T>  泛型
     * @return T
     * @throws BeansException
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 根据bean类名获取bean实例
     *
     * @param clz bean实例类名
     * @param <T> 泛型
     * @return T
     * @throws BeansException
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clz) throws BeansException {
        return (T) applicationContext.getBean(clz);
    }
}
