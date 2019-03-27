/**
 * Hola.YK Inc. Copyright (c) 2012-2015 All Rights Reserved.
 */
package co.b4pay.admin.common.web.interceptor;

import co.b4pay.admin.common.core.resource.ConfigResource;
import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.util.WebUtil;
import co.b4pay.admin.common.util.WebUtil;
import co.b4pay.admin.common.core.resource.ConfigResource;
import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.core.resource.ConfigResource;
import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.util.WebUtil;
import co.b4pay.admin.common.core.resource.ConfigResource;
import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.util.WebUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 * 全局拦截器
 *
 * @author YK
 * @version $Id: LogInterceptor.java, v 0.1 2014年12月12日 上午11:25:55 YK Exp $
 */
public class LogInterceptor extends HandlerInterceptorAdapter {
    //private static final Logger logger = LoggerFactory.getLogger("HTTP_REQUEST_LOG");

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");

    private static final String LOGIN_ID_KEY = "user_id";

    @Autowired
    private ConfigResource configResource;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 调试信息
        if (configResource.isShowRequestLog()) {
            log(request);
        }
        return true;
    }

    private void log(HttpServletRequest request) {
        StringBuilder logInfo = new StringBuilder();
        logInfo.append("[");
        logInfo.append(SDF.format(new java.util.Date()));
        Object uid = WebUtils.getSessionAttribute(request, LOGIN_ID_KEY);
        if (uid != null) {
            //logInfo.append(" UID:").append(uid);
            //logInfo.append(StringUtil.SPACE).append(StringUtil.leftPad(uid.toString(), 6, ":"));
            logInfo.append(StringUtil.SPACE).append(uid);
        }
        //logInfo.append(StringUtil.SPACE).append(WebUtil.getClientIp(request));
        logInfo.append("] ");
        logInfo.append(request.getMethod()).append(StringUtil.SPACE).append(request.getRequestURI());
        String queryString = WebUtil.isGet(request) ? request.getQueryString() : WebUtil.getQueryString(request);
        if (StringUtils.isNotBlank(queryString)) {
            logInfo.append("?").append(queryString);
        }
        System.out.println(logInfo.toString());
    }
}