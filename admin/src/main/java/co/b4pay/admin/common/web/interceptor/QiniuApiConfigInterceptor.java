
package co.b4pay.admin.common.web.interceptor;

import co.b4pay.admin.common.core.api.qiniu.QiniuApi;
import co.b4pay.admin.common.core.api.qiniu.QiniuApiConfig;
import co.b4pay.admin.common.core.api.qiniu.QiniuApi;
import co.b4pay.admin.common.core.api.qiniu.QiniuApiConfig;
import co.b4pay.admin.common.core.api.qiniu.QiniuApi;
import co.b4pay.admin.common.core.api.qiniu.QiniuApiConfig;
import co.b4pay.admin.common.core.api.qiniu.QiniuApi;
import co.b4pay.admin.common.core.api.qiniu.QiniuApiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 自动注入七牛云服务的配置信息
 *
 * @author YK
 * @version $Id v 0.1 2017年03月08日 09:25 Exp $
 */

public class QiniuApiConfigInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(QiniuApiConfigInterceptor.class);

    @Autowired
    private QiniuApi qiniuApi;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getMethod().isAnnotationPresent(QiniuApiConfig.class) || handlerMethod.getBeanType().isAnnotationPresent(QiniuApiConfig.class)) {
                request.setAttribute("QINIU_UPTOKEN", qiniuApi.getPublicUptoken());
                request.setAttribute("QINIU_DOMAIN", qiniuApi.getPublicDomain());
            }
        } else {
            //org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler
            logger.info(handler.toString() + "\tURI:::" + request.getRequestURI());
        }
    }
}

