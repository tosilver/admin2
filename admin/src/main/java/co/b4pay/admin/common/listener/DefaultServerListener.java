/**
 * Hola.YK Inc. Copyright (c) 2012-2013 All Rights Reserved.
 */
package co.b4pay.admin.common.listener;

import co.b4pay.admin.common.util.DriverManagerUtil;
import co.b4pay.admin.common.util.ToStringUtil;
import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

/**
 * 默认服务监听器
 *
 * @author YK
 * @version $Id: DefaultServerListener.java, v 0.1 2013年9月1日 上午1:04:52 YK Exp $
 * @see ContextLoaderListener
 */
public class DefaultServerListener extends ContextLoaderListener {
    /**
     * 日志定义
     */
    private static final Logger logger = LoggerFactory.getLogger(DefaultServerListener.class);

    // private ServletContext servletContext;

    /**
     * @see ContextLoaderListener#contextInitialized(ServletContextEvent)
     */
    @Override
    public void contextInitialized(final ServletContextEvent event) {
        if (logger.isInfoEnabled()) {
            logger.info("系统环境：" + System.getProperty("os.name"));
            // logger.info("服务器启动... \r\n" + ToStringUtil.toString(event));
        }
        super.contextInitialized(event);

        try {
            AbandonedConnectionCleanupThread.shutdown();//停止废连接清理线程
            DriverManagerUtil.deregister();//手动注销JDBC驱动程序
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        // this.servletContext = event.getServletContext();

        // 初始化网站配置
//        initWebsiteConfig();
        // 初始化自定义扩展插件
//        initPlgins();
        // 初始化任务队列
//        initJobQueue();
        // 初始化数据缓存
//        initDataCache();
    }

    /**
     * @see javax.servlet.ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        if (logger.isInfoEnabled()) {
            logger.info("服务器停止... \r\n" + ToStringUtil.toString(event));
        }

        try {
            AbandonedConnectionCleanupThread.shutdown();//停止废连接清理线程
            DriverManagerUtil.deregister();//手动注销JDBC驱动程序
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            super.contextDestroyed(event);
        }
    }

    /**
     * 初始化任务队列
     */
    private void initWebsiteConfig() {
        // 网站 - 标题
        // servletContext.setAttribute("website_title", PropUtil.getString("website.title"));
        // 网站 - 副标题
        // servletContext.setAttribute("website_subtitle", PropUtil.getString("website.subtitle"));

        // if (JFinal.me().init(null, servletContext) == false)
        // throw new RuntimeException("JFinal init error!");

        // DEBUG
        // System.out.println("DefaultServerListener");
        // System.out.println(org.slf4j.Logger.class.getClassLoader().getResource("org/slf4j/Logger.class"));
        // System.out.println(org.apache.commons.logging.impl.SLF4JLocationAwareLog.class.getClassLoader().getResource("org/apache/commons/logging/impl/SLF4JLocationAwareLog.class"));
        // System.out.println(org.slf4j.Marker.class.getClassLoader().getResource("org/slf4j/Marker.class"));
        // System.out.println(org.slf4j.spi.LocationAwareLogger.class.getClassLoader().getResource("org/slf4j/spi/LocationAwareLogger.class"));
    }

    /**
     * 初始化任务队列
     */
    private void initJobQueue() {
        // WebApplicationContext cxt =
        // ContextLoader.getCurrentWebApplicationContext();
        // WebApplicationContext cxt = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        // UserService userService = cxt.getBean(UserService.class);
        // AccountService accountService = cxt.getBean(AccountService.class);
        // AccountBankService accountBankService = cxt.getBean(AccountBankService.class);
        // AccountCashService accountCashService = cxt.getBean(AccountCashService.class);
        // BorrowInvestService borrowInvestService = cxt.getBean(BorrowInvestService.class);
        //
        // AccountJobQueue.init(userService, accountService, accountBankService);
        // CashJobQueue.init(accountCashService);
        // TenderJobQueue.init(borrowInvestService);
    }

    /**
     * 初始化插件
     */
    private void initPlgins() {
        // WebApplicationContext cxt = ContextLoader.getCurrentWebApplicationContext();
        // Plugins myPlugins = cxt.getBean("myPlugins", Plugins.class);
        // if (myPlugins == null) {
        // return;
        // }
        // List<IPlugin> pluginList = myPlugins.getPluginList();
        // if (pluginList == null || pluginList.isEmpty()) {
        // return;
        // }
        // for (IPlugin plugin : pluginList) {
        // plugin.start();
        // }
    }

    /**
     * 初始化数据缓存
     */
    private void initDataCache() {
//        try {
//            // 加载缓存
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
    }
}