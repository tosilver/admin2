/**
 * Hola.YK Inc. Copyright (c) 2012-2013 All Rights Reserved.
 */
package co.b4pay.admin.common.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 默认分配器
 *
 * @author YK
 * @version $Id: DefaultDispatcherServlet.java, v 0.1 2013年9月3日 下午11:03:09 YK Exp $
 * @see DispatcherServlet
 */
public class DefaultDispatcherServlet extends DispatcherServlet {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1043912359784922922L;

    /**
     * 日志定义
     */
    private static final Logger logger = LoggerFactory.getLogger(DefaultDispatcherServlet.class);

    /**
     * @see javax.servlet.GenericServlet#init(ServletConfig)
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        if (logger.isInfoEnabled()) {
            logger.info("默认分配器初始化。。。");
        }
        super.init(config);
    }

    /**
     * @see DispatcherServlet#doDispatch(HttpServletRequest,
     * HttpServletResponse)
     */
    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.doDispatch(request, response);
    }
}