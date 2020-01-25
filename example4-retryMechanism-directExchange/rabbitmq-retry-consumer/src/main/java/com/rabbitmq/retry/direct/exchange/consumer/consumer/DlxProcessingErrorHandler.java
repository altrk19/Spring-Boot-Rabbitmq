package com.rabbitmq.retry.direct.exchange.consumer.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.retry.direct.exchange.consumer.rabbitmq.RabbitmqHeader;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;

@Slf4j
public class DlxProcessingErrorHandler {
    private static final int MAX_RETRY_COUNT = 3;

    private String deadExchangeName;

    public DlxProcessingErrorHandler(String deadExchangeName) {
        super();
        if (StringUtils.isEmpty(deadExchangeName)) {
            throw new IllegalArgumentException("Must define dlx exchange name");
        }
        this.deadExchangeName = deadExchangeName;
    }

    public String getDeadExchangeName() {
        return deadExchangeName;
    }

    public int getMaxRetryCount() {
        return MAX_RETRY_COUNT;
    }

    public void handleErrorProcessingMessage(Message message, Channel channel) {
        RabbitmqHeader rabbitMqHeader = new RabbitmqHeader(message.getMessageProperties().getHeaders());

        try {
            if (rabbitMqHeader.getFailedRetryCount() >= MAX_RETRY_COUNT) {
                // publish to dead and ack
                log.warn("(DEAD) Error at :{}  on retry :{} for message :{}", new Date(), rabbitMqHeader.getFailedRetryCount(), message);

                //eğer deneme sayısını geçtiysek mesaj dead exchange'e publish ediliyor ve rabbitmq'ya ack dönülüyor.
                channel.basicPublish(getDeadExchangeName(), message.getMessageProperties().getReceivedRoutingKey(),
                        null, message.getBody());
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                log.warn("(REQUEUE) Error at :{}  on retry :{} for message :{}", new Date(), rabbitMqHeader.getFailedRetryCount(), message);
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            }
        } catch (IOException e) {
            log.warn("(HANDLER-FAILED) Error at :{}  on retry :{} for message :{}", new Date(), rabbitMqHeader.getFailedRetryCount(), message);
        }
    }
}
