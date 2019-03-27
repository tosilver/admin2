/*
 * Copyright (C), 2002-2016, 苏宁易购电子商务有限公司
 * FileName: Util.java
 * Author:   16031333
 * Date:     2016年5月26日 上午9:50:44
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package co.b4pay.admin.common.YfbUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author 16031333
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class Util {

    public static String generalBatchNo() {
        String dateStr = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        return new StringBuffer().append("HQ").append(getRandomString(10, dateStr)).toString();
    }

    /**
     * 生成随机数字符串
     *
     * @param length 表示生成字符串的长度, dateStr 基本字符串
     * @return StringBuffer
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    public static String getRandomString(int length, String dateStr) { // length表示生成字符串的长度
        String base = dateStr;
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 生成不重复明细序列号
     *
     * @param
     * @return StringBuffer
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    public static StringBuffer generalDetailNo() {
        String dateStr = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        return new StringBuffer().append("D").append(getRandomString(11, dateStr));
    }

    public static void main(String[] args) {
        String s = Util.generalBatchNo();
        //System.out.println(s);
    }

}
