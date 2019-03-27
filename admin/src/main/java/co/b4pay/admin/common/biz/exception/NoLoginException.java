package co.b4pay.admin.common.biz.exception;

/**
 * <b>function:</b>未登录
 *
 * @author YK
 */
public class NoLoginException extends WebException {

    /**
     *
     */
    private static final long serialVersionUID = 3659869559357609846L;

    public NoLoginException() {
        this("未登录 !");
    }

    public NoLoginException(String msg) {
        super("NoLogin Exception:  " + msg);
    }
}
