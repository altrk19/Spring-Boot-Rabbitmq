package com.rabbitmq.errorhandling.producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.errorhandling.producer.entity.PictureModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyPictureProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(PictureModel p) {
        String pictureStr = null;
        try {
            pictureStr = objectMapper.writeValueAsString(p);
        } catch (JsonProcessingException e) {
            log.error("Exception", e);
        }
        rabbitTemplate.convertAndSend("x.mypicture", "", pictureStr);
    }
}
