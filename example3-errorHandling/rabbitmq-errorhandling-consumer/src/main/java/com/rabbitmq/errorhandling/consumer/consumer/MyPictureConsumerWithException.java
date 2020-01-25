package com.rabbitmq.errorhandling.consumer.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.errorhandling.consumer.entity.PictureModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MyPictureConsumerWithException {

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger log = LoggerFactory.getLogger(MyPictureConsumerWithException.class);

    @RabbitListener(queues = "q.mypicture.image")
    public void listen(String message) throws IOException {
        PictureModel p = objectMapper.readValue(message, PictureModel.class);
        if (p.getSize() > 5000) {
            throw new AmqpRejectAndDontRequeueException("Picture is too large" + p);
            //Rabbitmq client'ı java'nın exception attığını anlıyor ki mesajı dlx'e gönderiyor.
        }
        log.debug("On mypicture.image : {}", p);
    }

}

//Bu yöntemde exception fırlattık ve rabbitmq mesajı dlx'e gönderdi
