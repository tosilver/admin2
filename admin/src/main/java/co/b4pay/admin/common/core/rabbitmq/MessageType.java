package co.b4pay.admin.common.core.rabbitmq;

/**
 * 消息模板类型
 *
 * @author YK
 * @version $Id v 0.1 2016年11月15日 15:16 Exp $
 */
public enum MessageType {

    /**
     * 认证审核通知
     */
    CERTIFY_AUDIT_NOTICE,

    /**
     * 申请通知
     */
    CAR_APPLY_NOTICE,

    /**
     * 用户支付通知
     */
    USER_PAYMENT_NOTICE,

    /**
     * 用户咨询消息
     */
    USER_MESSAGE_NOTICE,

    /**
     * 验证码短信
     */
    VERIFY_CODE_SMS,

    /**
     * 友盟消息推送
     */
    UM_MESSAGE_PUSH
}
