package co.b4pay.admin.common.biz.exception;

import co.b4pay.admin.entity.base.BaseException;

/**
 * <b>function:</b>签名异常
 *
 * @author YK
 */
public class SignatureException extends BaseException {
    private static final long serialVersionUID = 3659869519959644146L;

    public SignatureException() {
        this("signature exception !");
    }

    public SignatureException(String msg) {
        super("Signature Exception:  " + msg);
    }
}
