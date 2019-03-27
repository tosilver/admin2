package co.b4pay.admin.common.core.security.encoder;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5密码加密
 *
 * @author YK
 * @version $Id: Md5PwdEncoder.java, v 0.1 2015-1-3 下午6:53:26 YK Exp $
 */
public class Md5PwdEncoder implements PwdEncoder {

    /**
     * 混淆码。防止破解。
     */
    private String defaultSalt;

    /**
     * 是否大写。
     */
    private boolean uppercase;

    /**
     * 密码加密
     *
     * @param rawPass 未加密密码，null作为空串
     * @return 加密后密码
     */
    @Override
    public String encrypt(final String rawPass) {
        return encrypt(rawPass, defaultSalt);
    }

    /**
     * 密码加密
     *
     * @param rawPass 未加密密码，null作为空串
     * @param salt    混淆码
     * @return
     */
    @Override
    public String encrypt(final String rawPass, final String salt) {
        final String saltedPass = mergePasswordAndSalt(rawPass, salt, false);
        final MessageDigest messageDigest = getMessageDigest();
        byte[] digest;
        try {
            digest = messageDigest.digest(saltedPass.getBytes("UTF-8"));
        } catch (final UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 not supported!");
        }
        return uppercase ? String.valueOf(Hex.encodeHex(digest)).toUpperCase() : String.valueOf(Hex.encodeHex(digest));
    }

    /**
     * 验证密码是否正确
     *
     * @param encPass 加密密码
     * @param rawPass 未加密密码，null作为空串
     * @return true:密码正确；false:密码错误
     */
    @Override
    public boolean isValid(final String encPass, final String rawPass) {
        return isValid(encPass, rawPass, defaultSalt);
    }

    /**
     * 验证密码是否正确
     *
     * @param encPass 加密密码
     * @param rawPass 未加密密码，null作为空串
     * @param salt    混淆码
     * @return true:密码正确；false:密码错误
     */
    @Override
    public boolean isValid(final String encPass, final String rawPass, final String salt) {
        if (encPass == null) {
            return false;
        }
        final String pass2 = encrypt(rawPass, salt);
        return encPass.equals(pass2);
    }

    /**
     * 获取算法摘要
     *
     * @return
     */
    protected final MessageDigest getMessageDigest() {
        final String algorithm = "MD5";
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm [" + algorithm + "]");
        }
    }

    /**
     * 合并密码和混淆码
     *
     * @param password
     * @param salt
     * @param strict
     * @return
     */
    protected String mergePasswordAndSalt(String password, final String salt, final boolean strict) {
        if (password == null) {
            password = "";
        }
        if (salt == null || "".equals(salt)) {
            return password;
        }
        if (strict && (salt.contains("{") || salt.contains("}"))) {
            throw new IllegalArgumentException("Cannot use { or } in salt.toString()");
        }
        return password + "{" + salt.toString() + "}";
    }

    /**
     * Getter method for property <tt>defaultSalt</tt>.
     *
     * @return property value of defaultSalt
     */
    public String getDefaultSalt() {
        return defaultSalt;
    }

    /**
     * Setter method for property <tt>defaultSalt</tt>.
     *
     * @param defaultSalt value to be assigned to property defaultSalt
     */
    public void setDefaultSalt(final String defaultSalt) {
        this.defaultSalt = defaultSalt;
    }

    /**
     * Getter method for property <tt>uppercase</tt>.
     *
     * @return property value of uppercase
     */
    public boolean isUppercase() {
        return uppercase;
    }

    /**
     * Setter method for property <tt>uppercase</tt>.
     *
     * @param uppercase value to be assigned to property uppercase
     */
    public void setUppercase(final boolean uppercase) {
        this.uppercase = uppercase;
    }

    public static void main(final String args[]) {
        // final Md5PwdEncoder md5PwdEncoder = new Md5PwdEncoder();
        // md5PwdEncoder.setUppercase(true);
        // final String password = md5PwdEncoder.encrypt("123456");
        // System.out.println(password);
    }
}
