package co.b4pay.admin.common.constants;

import java.text.DecimalFormat;

/**
 * Created by john on 2018/4/26.
 */
public final class Constants {

    public static final String MERCHANT_ID = "merchantId";

    @Deprecated
    /** 默认字符类型 */
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * UTF-8字符类型
     */
    public static final String CHARSET_UTF8 = "UTF-8";

    /**
     * 验证码
     */
    public static final String CAPTCHA = "captcha";

    /**
     * 请求转发标示 forward:
     */
    public static final String FORWARD = "forward:";

    /**
     * 请求重定向标示 redirect:
     */
    public static final String REDIRECT = "redirect:";

    /**
     * 请求重定向隐藏标示 _redirect_
     */
    public static final String REDIRECT_HIDDEN = "_redirect_";

    /**
     * message标识
     */
    public static final String MESSAGE = "message";

    /**
     * BIZ错误信息提示
     */
    public static final String ERROR_MSG_BIZ = "系统异常，请稍后再试";
    /**
     * 加密解密相关常量
     */
    public static final String SCAN_LIFE = "http://localhost:9988/notify/updatePersonalCodeUrl.do";

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

        /**
         * 加密解密相关常量
         */


    }
}
