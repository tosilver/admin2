package co.b4pay.admin.common.biz.exception;

import co.b4pay.admin.entity.base.BaseException;


/**
 * <b>function:</b>定义数据持久层异常类
 *
 * @author YK
 */
public class DaoException extends BaseException {

    /**
     *
     */
    private static final long serialVersionUID = 3653689519957644146L;

    /*
     * DAO (Data Access Objects) 数据访问对象
     */
    public DaoException(String msg) {
        this(msg, null);
    }

    public DaoException(Exception e) {
        super(e);
    }

    public DaoException(String msg, Exception e) {
        super("数据访问异常:" + msg, e);
    }
}
