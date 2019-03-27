package co.b4pay.admin.common.util.myutil.propert;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * 读取配置文件工具类
 * Created with IntelliJ IDEA
 * Created By AZain
 * Date: 2018-01-19
 * Time: 11:50
 */
@Slf4j
public class PropertiesConfig {

    private static final Logger log = LoggerFactory.getLogger(PropertiesConfig.class);


    //动态配置文件
    private static Properties props;

    //默认jar外部文件路径
    private static String externalPath = System.getProperty("user.dir") + File.separator + "resources" + File.separator;

    /**
     * 默认只加载default.properties
     */
    static {
        props = new Properties();
        props = getProperties(null);
    }

    /**
     * 根据文件名读取配置文件信息
     *
     * @param fileName
     * @return
     */
    public static Properties getProperties(String fileName) {
        Properties p = new Properties();
        InputStream in = null;
        if (StringUtils.isNotBlank(fileName)) {
            //先读取外部配置文件
            log.info("外部配置文件路径：" + externalPath + fileName);
            File propertiesFile = new File(externalPath + fileName);
            if (propertiesFile.exists()) {
                try {
                    in = new FileInputStream(propertiesFile);
                } catch (FileNotFoundException e) {
                    log.error(e.getMessage(), e);
                    e.printStackTrace();
                }
            }
            if (in == null) {
                log.info("读取外部配置文件失败，读取内部配置文件。");
                in = PropertiesConfig.class.getClassLoader().getResourceAsStream(fileName);
            }
        }
        if (in == null) {
            log.info("读取外部配置文件失败，读取内部【默认】配置文件。");
            in = PropertiesConfig.class.getClassLoader().getResourceAsStream("default.properties");
        }
        InputStreamReader inputStream = null;
        try {
            inputStream = new InputStreamReader(in, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        try {
            p.load(inputStream);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return p;
    }

    /**
     * 根据配置属性key获取属性值
     *
     * @param propertiesKeyName 属性key
     * @return
     */
    public static String getProperty(String propertiesKeyName) {
        return getProperty(propertiesKeyName, null);
    }

    /**
     * 根据配置属性key获取属性值
     *
     * @param propertiesKeyName      属性key
     * @param propertiesDefaultValue 默认值
     * @return
     */
    public static String getProperty(String propertiesKeyName, String propertiesDefaultValue) {
        return getProperty(null, propertiesKeyName, propertiesDefaultValue);
    }

    /**
     * 根据配置文件名、配置属性key获取属性值
     *
     * @param propertiesFileName     配置文件名
     * @param propertiesKeyName      属性key
     * @param propertiesDefaultValue 默认值
     * @return
     */
    public static String getProperty(String propertiesFileName, String propertiesKeyName, String propertiesDefaultValue) {
        if (StringUtils.isNotBlank(propertiesFileName)) {
            props = getProperties(propertiesFileName);
        }
        return props.getProperty(propertiesKeyName, propertiesDefaultValue);
    }

}
