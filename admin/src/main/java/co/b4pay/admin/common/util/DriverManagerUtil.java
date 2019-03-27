package co.b4pay.admin.common.util;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

/**
 * @author YK
 * @version $Id v 0.1 2016年11月23日 11:20 Exp $
 */
public class DriverManagerUtil {
    /**
     * 注销JDBC驱动程序
     * <p>
     * This manually deregisters JDBC driver, which prevents Tomcat 7 from complaining about memory leaks wrto this class
     */
    public static void deregister() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                //logger.info(String.format("deregistering jdbc driver: %s", driver));
                System.out.println(String.format("deregistering jdbc driver: %s", driver));
            } catch (Exception e) {
                //logger.error(String.format("Error deregistering driver %s", driver), e);
                System.err.println(String.format("Error deregistering driver %s", driver));
                e.printStackTrace(System.err);
            }
        }
    }
}
