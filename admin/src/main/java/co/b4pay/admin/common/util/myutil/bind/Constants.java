package co.b4pay.admin.common.util.myutil.bind;

import co.b4pay.admin.common.util.myutil.propert.PropertiesConfig;


import java.text.DecimalFormat;

/**
 * 常用参数类
 * Created with IntelliJ IDEA
 * Created By AZain
 * Date: 2018-01-22
 * Time: 14:53
 */
public class Constants {

    /**
     * 加密解密相关常量
     */
    public static class Encrypt {
        //算法类型
        public static final String ALGORITHM_NAME_DES = PropertiesConfig.getProperty("algorithm.name.des");
        //加密解密算法字符编码类型
        public static final String CHARSET_TYPE = PropertiesConfig.getProperty("encrypt.charset.type");
        //DES秘钥
        public static final String DES_SECRET_KEY = PropertiesConfig.getProperty("des.secret.key");
        //DES安全策略
        public static final String DES_SECURITY_POLICY = PropertiesConfig.getProperty("des.security.policy");
    }

    public static class File {
        //对应系统文件路径间隔
        public static final String FILE_SEPARATOR = System.getProperty("file.separator");

        public static final String FILE_POINT = ".";

        public static final String FILE_POSTFIX_XML = "xml";

        public static String FILE_POSTFIX_EXCEL2003 = "xls";

        public static String FILE_POSTFIX_EXCEL2007 = "xlsx";

        public static String FILE_POSTFIX_ZIP = "zip";

        public static DecimalFormat DECIMAL_FORMAT_DF = new DecimalFormat("0");// 格式化 number String

        public static DecimalFormat DECIMAL_FORMAT_NF = new DecimalFormat("0.00");// 格式化数字
    }


}
