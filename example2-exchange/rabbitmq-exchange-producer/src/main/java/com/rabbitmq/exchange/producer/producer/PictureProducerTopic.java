package com.rabbitmq.exchange.producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.exchange.producer.entity.PictureModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PictureProducerTopic {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(PictureModel p) throws JsonProcessingException {
        StringBuilder sb = new StringBuilder();
        sb.append(p.getSource());
        sb.append(".");

        if (p.getSize() > 4000) {
            sb.append("large");
        } else {
            sb.append("small");
        }
        sb.append(".");
        sb.append(p.getType());
        //mobile.large.jpg      ->     q.picture.filter    q.picture.image

        String pictureStr = objectMapper.writeValueAsString(p);
        String routingKey = sb.toString();
        rabbitTemplate.convertAndSend("x.picture2", routingKey, pictureStr);
    }
}
