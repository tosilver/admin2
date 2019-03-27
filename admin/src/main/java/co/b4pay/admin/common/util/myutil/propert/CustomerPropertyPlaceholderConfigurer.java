package co.b4pay.admin.common.util.myutil.propert;

import co.b4pay.admin.common.util.myutil.DESUtil;
import co.b4pay.admin.common.util.myutil.bind.Constants;
import co.b4pay.admin.controller.merchant.ConsumeController;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 自定义PropertyPlaceHolderConfigurer
 * Created with IntelliJ IDEA
 * Created By AZain
 * Date: 2018-02-05
 * Time: 10:40
 */
@Slf4j
public class CustomerPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    private static final Logger log = LoggerFactory.getLogger(CustomerPropertyPlaceholderConfigurer.class);

    //存放配置文件properties的Map
    private Map<String, String> PropertiesMap;

    //加密的List
    private List<String> encryptPropNames;

    /**
     * 读取properties时，缓存配置至变量里
     *
     * @param beanFactoryToProcess
     * @param props
     * @throws BeansException
     */
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        //在spring读取配置文件时，存储一份至map中
        PropertiesMap = new LinkedHashMap<>();
        for (Object key : props.keySet()) {
            String keyName = key.toString();
            PropertiesMap.put(keyName, props.getProperty(keyName));
        }
    }

    /**
     * 解密配置文件里加密的字段
     *
     * @param propertyName
     * @param propertyValue
     * @return
     */
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (encryptPropNames.contains(propertyName)) {
            // 对已加密的字段进行解密工作
            String decryptPropertyValue = null;
            try {
                decryptPropertyValue = DESUtil.getDecryptString(propertyValue);
            } catch (Exception e) {
                log.error("配置文件加密字段解密失败！");
                e.printStackTrace();
            }
            return decryptPropertyValue;
        } else {
            return propertyValue;
        }
    }

    /**
     * 对外提供的全局变量查询接口
     *
     * @param key
     * @return
     */
    public String getContextProperty(String key) {
        return PropertiesMap.get(key);
    }

    /**
     * 提供xml的自定义设置配置文件的方法
     * <property name="customPropertyFiles">
     * <list>
     * <value>[filepath]test.properties</value>
     * </list>
     * </property>
     *
     * @param customPropertyFiles
     */
    public void setCustomPropertyFiles(List<String> customPropertyFiles) throws IOException {
        Properties properties = new Properties();
        for (String customPropertyFile : customPropertyFiles) {
            customPropertyFile = customPropertyFile.replace("[FilePath]", Constants.File.FILE_SEPARATOR);
            Properties pro = PropertiesConfig.getProperties(customPropertyFile);
            properties.putAll(pro);
        }
        //关键方法，调用父类中的方法，通过这个方法将自定义在家的properties文件加入spring
        this.setProperties(properties);
    }

    /**
     * 提供xml的自定义设置加密字段的方法
     * <property name="customEncryptPropertyNames">
     * <list>
     * <value></value>
     * </list>
     * </property>
     *
     * @param encryptPropNames
     */
    public void setCustomEncryptPropertyNames(List<String> encryptPropNames) {
        this.encryptPropNames = encryptPropNames;
    }
}
