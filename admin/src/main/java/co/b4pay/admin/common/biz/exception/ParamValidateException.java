package co.b4pay.admin.common.biz.exception;

import co.b4pay.admin.entity.enums.ResultCode;


/**
 * 参数校验异常
 *
 * @author YK
 * @version $Id: ParamValidateException.java, v 0.1 2013-9-4 下午1:00:36 YK Exp $
 */
public class ParamValidateException extends BizException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1686005898705074398L;

//    /**
//     * 参数校验异常 - 默认构造方法
//     */
//     public ParamValidateException() {
//        super(CommonResultCode.PARAMETER_INVALID);
//     }

    /**
     * 参数校验异常
     *
     * @param message
     */
    public ParamValidateException(String message) {
        super(message);
    }

    public ParamValidateException(ResultCode resultCode) {
        super(resultCode);
    }
}
