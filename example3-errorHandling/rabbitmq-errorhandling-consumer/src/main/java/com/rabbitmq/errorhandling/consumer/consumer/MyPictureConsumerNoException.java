package com.rabbitmq.errorhandling.consumer.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.errorhandling.consumer.entity.PictureModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

//@Service
public class MyPictureConsumerNoException {

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger log = LoggerFactory.getLogger(MyPictureConsumerNoException.class);

    @RabbitListener(queues = "q.mypicture.image")
    public void listen(Message message, Channel channel) throws IOException {
        PictureModel p = objectMapper.readValue(message.getBody(), PictureModel.class);
        if (p.getSize() > 5000) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
        log.debug("On mypicture.image : {}", p);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
//Bu yöntemde mesajı manuel olarak reject ettik ve rabbitmq mesajı dlx'e gönderdi
//add this parameters to application.properties
//spring.rabbitmq.listener.direct.acknowledge-mode: manual
//spring.rabbitmq.listener.listener.simple.acknowledge-mode: manual