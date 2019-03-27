package co.b4pay.admin.common.biz.exception;

import co.b4pay.admin.entity.base.BaseException;

/**
 * <b>function:</b>定义控制层异常类
 *
 * @author YK
 */
public class WebException extends BaseException {

    /**
     *
     */
    private static final long serialVersionUID = 3659869519957609846L;

    public WebException() {
        this("no msg !");
    }

    public WebException(String msg) {
        super("Web Exception:  " + msg);
    }

    public WebException(String msg, Exception e) {
        super("Web Exception:  " + msg, e);
    }

}
