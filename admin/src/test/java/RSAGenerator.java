import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

import static com.suning.epps.merchantsignature.common.RSAUtil.*;


public class RSAGenerator {

    @Test
    public void rsa() {
        // 生成一对公私钥，路径可以自己填
        try {
            createKey("D:/RSA/public.key", "D:/RSA/private.key", 1024);
            // 转换为公钥对象
            PublicKey pubKey = resolvePublicKey("D:/RSA/public.key");
            // 转换为私钥对象
            PrivateKey priKey = resolvePrivateKey("D:/RSA/private.key");
            // Base64编码后的公钥字符串
            System.out.println("公钥：" + Base64.encodeBase64String(pubKey.getEncoded()));
            // Base64编码后的私钥字符串
            System.out.println("私钥：" + Base64.encodeBase64String(priKey.getEncoded()));
            String data = "B64DC35297E509D8078FDD64DDBBED73";
            // RSA加签
            String signData = sign(data, priKey);
            System.out.println("签名值为：" + signData);
            // RSA验签
            boolean result = vertiy(data, signData, pubKey);
            System.out.println("验签结果为：" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
