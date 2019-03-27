package co.b4pay.admin.common.core.resource;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;

/**
 * 消息生产者
 * <p>
 * Created by YK on 2016/11/14 15:27.
 */
public class ResourceUpdateProducer {
    private static final Logger logger = LoggerFactory.getLogger(ResourceUpdateProducer.class);

    /**
     * 系统配置资源
     */
    private static final String ROUTING_KEY = "SYSTEM_CONFIG_RESOURCE";

    private AmqpTemplate amqpTemplate;

    public void send(String key, Object value) {
        JSONObject messageBody = new JSONObject();
        messageBody.put(key, value);
        amqpTemplate.convertAndSend(ROUTING_KEY, messageBody);
    }

    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }
}