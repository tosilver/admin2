package co.b4pay.admin.common.core.security.encoder;

/**
 * 密码加密接口
 *
 * @author YK
 * @version $Id: PwdEncoder.java, v 0.1 2015-1-3 下午6:53:38 YK Exp $
 */
public interface PwdEncoder {
    /**
     * 密码加密
     *
     * @param rawPass 未加密密码，null作为空串
     * @return 加密后密码
     */
    public String encrypt(String rawPass);

    /**
     * 密码加密
     *
     * @param rawPass 未加密密码，null作为空串
     * @param salt    混淆码
     * @return
     */
    public String encrypt(String rawPass, String salt);

    /**
     * 验证密码是否正确
     *
     * @param encPass 加密密码
     * @param rawPass 未加密密码，null作为空串
     * @return true:密码正确；false:密码错误
     */
    public boolean isValid(String encPass, String rawPass);

    /**
     * 验证密码是否正确
     *
     * @param encPass 加密密码
     * @param rawPass 未加密密码，null作为空串
     * @param salt    混淆码
     * @return true:密码正确；false:密码错误
     */
    public boolean isValid(String encPass, String rawPass, String salt);
}
