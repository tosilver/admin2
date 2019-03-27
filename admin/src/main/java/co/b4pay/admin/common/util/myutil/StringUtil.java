package co.b4pay.admin.common.util.myutil;

/**
 * 字符串工具类
 * Created with IntelliJ IDEA
 * Created By AZain
 * Date: 2017-12-11
 * Time: 9:06
 */
public class StringUtil {

    /**
     * 半角转全角
     *
     * @param input
     * @return
     */
    public static String ToSBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127) c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }


    /**
     * 全角转半角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

}
