package co.b4pay.admin.common.core.api.qiniu;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;

/**
 * 七牛云服务配置自动注入
 * 有文件上传的页面需在方法前配置
 *
 * @author YK
 * @version $Id v 0.1 2016年12月08日 10:37 Exp $
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {METHOD})
public @interface QiniuApiConfig {
}