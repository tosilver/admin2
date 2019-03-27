package co.b4pay.admin.controller.Utils;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.ApkFile;

import java.security.SignatureException;

@MyBatisDao
public interface Signature {
    /**
     * 校验签名
     *
     * @param content   签名内容
     * @param signature 签名字符串
     * @param publicKey 签名公钥
     * @param charset   签名编码
     * @return
     * @throws SignatureException
     */
    boolean check(String content, String signature, String publicKey, String charset) throws SignatureException;

    /**
     * 签名
     *
     * @param content    签名内容
     * @param privateKey 签名私钥
     * @param charset    签名编码
     * @return
     * @throws SignatureException
     */
    String sign(String content, String privateKey, String charset) throws SignatureException;
}
