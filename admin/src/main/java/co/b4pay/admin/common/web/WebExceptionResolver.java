package co.b4pay.admin.common.web;

import co.b4pay.admin.common.biz.exception.BizException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: YK
 * @createDate: 2011-09-20
 * @function: 自定义异常处理，集中处理未捕获的异常
 */
public class WebExceptionResolver implements HandlerExceptionResolver {
    private static final Logger logger = Logger.getLogger(WebExceptionResolver.class);

    // private static final ModelAndView REDIRECT_ERROR_PAGE = new ModelAndView(Constants.REDIRECT + "/500.html");

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // org.springframework.web.method.HandlerMethod handlerMethod = (HandlerMethod) handler;
        // System.err.println(ToStringUtil.toString(handlerMethod));
        // System.err.println(ex != null && ex.getClass().isAssignableFrom(BizException.class));
        // return null;
        // if (ex != null && ex.getClass().isAssignableFrom(BizException.class)) {
        // writeErrorMsg(request, response, ex.getMessage());
        // return null;
        // }
        // if (ex!=null && ex.getClass().isAssignableFrom(ClientAbortExcep)) {
        //
        // }

        // System.err.println(ex.getCause().getClass().isAssignableFrom(java.net.SocketException.class));
        // ClientAbortException: java.net.SocketException: Connection reset by peer: socket write error
        if (!(ex.getCause() instanceof java.net.SocketException)) {
            logger.error("Catch Exception: " + ex.getMessage(), ex);
        }
        if (ex.getCause() instanceof BizException) {

        }
        return new ModelAndView("error/500");

        // 可将异常写入数据库，以备系统管理员维护
        // Map<String, String> map = new HashMap<String, String>();
        // map.put("msg", e.getMessage());

        // 是否为ajax请求
        // if (WebUtil.isAjax(request)) {
        // writeErrorMsg(request, response, null);
        // return new ModelAndView();
        // }
        // if (e instanceof AuthorizationException) {
        // response.setStatus(401);// 无权限异常 主要用于ajax请求返回
        // response.addHeader("Error-Json", "{code:401,msg:'nopermission',script:''}");
        // response.setContentType("text/html;charset=utf-8");
        // if ("XMLHttpRequest".equals(requestType)) {
        // return new ModelAndView();
        // }
        // return new ModelAndView("redirect:/401.html");
        // }
    }

    @SuppressWarnings("unused")
    private void writeErrorMsg(HttpServletRequest request, HttpServletResponse response, String msg) {
        if (!response.isCommitted()) {
            if (StringUtils.isBlank(msg)) {
                msg = "系统异常！";
            }
            response.reset();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            try {
                response.getWriter().write("{\"status\":0,\"msg\":\"" + msg + "\"}");
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
