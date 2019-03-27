package co.b4pay.admin.common.util.myutil;

import co.b4pay.admin.common.util.myutil.bind.Constants;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * DES加密解密工具类
 * Created with IntelliJ IDEA
 * Created By AZain
 * Date: 2018-01-22
 * Time: 16:24
 */
public class DESUtil {

    private static Key key;

    static {
        try {
            // 生成DES算法对象
            KeyGenerator generator = KeyGenerator.getInstance(Constants.Encrypt.ALGORITHM_NAME_DES);
            // 运用SHA1安全策略
            SecureRandom secureRandom = SecureRandom.getInstance(Constants.Encrypt.DES_SECURITY_POLICY);
            // 设置上密钥种子
            secureRandom.setSeed(Constants.Encrypt.DES_SECRET_KEY.getBytes());
            // 初始化基于SHA1的算法对象
            generator.init(secureRandom);
            // 生成密钥对象
            key = generator.generateKey();
            generator = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取加密后的信息
     *
     * @param str
     * @return
     */
    public static String getEncryptString(String str) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // 基于BASE64编码，接收byte[]并转换成String
        BASE64Encoder base64encoder = new BASE64Encoder();
        // 按UTF8编码
        byte[] bytes = str.getBytes(Constants.Encrypt.CHARSET_TYPE);
        // 获取加密对象
        Cipher cipher = Cipher.getInstance(Constants.Encrypt.ALGORITHM_NAME_DES);
        // 初始化密码信息
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 加密
        byte[] doFinal = cipher.doFinal(bytes);
        // byte[]to encode好的String并返回
        return base64encoder.encode(doFinal);
    }

    /**
     * 获取解密之后的信息
     *
     * @param str
     * @return
     */
    public static String getDecryptString(String str) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // 基于BASE64编码，接收byte[]并转换成String
        BASE64Decoder base64decoder = new BASE64Decoder();
        // 将字符串decode成byte[]
        byte[] bytes = base64decoder.decodeBuffer(str);
        // 获取解密对象
        Cipher cipher = Cipher.getInstance(Constants.Encrypt.ALGORITHM_NAME_DES);
        // 初始化解密信息
        cipher.init(Cipher.DECRYPT_MODE, key);
        // 解密
        byte[] doFinal = cipher.doFinal(bytes);
        // 返回解密之后的信息
        return new String(doFinal, Constants.Encrypt.CHARSET_TYPE);
    }
}
