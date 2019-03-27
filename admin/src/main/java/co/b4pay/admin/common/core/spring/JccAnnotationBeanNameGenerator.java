package co.b4pay.admin.common.core.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**
 * 自定义Spring注解bean的命名策略工具类 自定义Spring注解默认的bean命名策略修改为类的全路径
 *
 * @author cc
 */
public class JccAnnotationBeanNameGenerator extends AnnotationBeanNameGenerator {

    @Override
    protected String buildDefaultBeanName(BeanDefinition definition) {
        return definition.getBeanClassName();
    }

}
