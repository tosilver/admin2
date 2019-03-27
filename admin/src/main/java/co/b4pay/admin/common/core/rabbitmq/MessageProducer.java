package co.b4pay.admin.common.core.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;

import java.util.Map;

/**
 * 消息生产者
 * <p>
 * Created by YK on 2016/11/14 15:27.
 */
public class MessageProducer {
    //private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    private AmqpTemplate amqpTemplate;

    public void sendMessage(MessageType routingKey, Map<String, Object> message) {
        amqpTemplate.convertAndSend(routingKey.name(), message);
    }

    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }
}