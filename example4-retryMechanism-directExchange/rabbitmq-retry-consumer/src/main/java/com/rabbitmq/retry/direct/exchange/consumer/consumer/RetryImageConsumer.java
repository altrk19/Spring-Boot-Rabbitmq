package com.rabbitmq.retry.direct.exchange.consumer.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.retry.direct.exchange.consumer.entity.PictureModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class RetryImageConsumer {

    private static final String DEAD_EXCHANGE_NAME = "x.guideline.dead";

    private DlxProcessingErrorHandler dlxProcessingErrorHandler;

    private ObjectMapper objectMapper;

    public RetryImageConsumer() {
        this.objectMapper = new ObjectMapper();
        this.dlxProcessingErrorHandler = new DlxProcessingErrorHandler(DEAD_EXCHANGE_NAME);
    }

    @RabbitListener(queues = "q.guideline.image.work")
    public void listen(Message message, Channel channel) throws InterruptedException, IOException {
        try {
            PictureModel pictureModel = objectMapper.readValue(message.getBody(), PictureModel.class);
            // process the image
            if (pictureModel.getSize() > 9000) {
                // throw exception, we will use DLX handler for retry mechanism
                throw new IllegalArgumentException("Size too large");
            } else {
                log.info("Creating thumbnail & publishing :{}", pictureModel);
                // you must acknowledge that message already processed
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        } catch (IllegalArgumentException e) {
            log.warn("Error processing message :{} :{}", new String(message.getBody()), e.getMessage());
            dlxProcessingErrorHandler.handleErrorProcessingMessage(message, channel);
        }
    }
}
//Reject edilen mesaj'ı rabbitmq wait queue'suna gönderir.ttl süresi(30sn) dolduktan sonra dlx mekanizması ile tekrardan work
//queue'suna gönderir. 3. tekrardan sonra consumer reject etmek yerine dead queue'suna mesajı publish eder. rabbitmq'da o exchangeê
//mesajı gönderir ve ardından bind olan queue'ya gödnerir.
