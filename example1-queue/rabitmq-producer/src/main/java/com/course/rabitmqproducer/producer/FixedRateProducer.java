package com.course.rabitmqproducer.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FixedRateProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Logger logger = LoggerFactory.getLogger(FixedRateProducer.class);
    private int i = 0;

    @Scheduled(fixedRate = 500) //every 0.5 seconds
    public void sendMessage() {
        i++;
        logger.debug("Sended message i:{}", i);
        rabbitTemplate.convertAndSend("course.fixedrate", "fixed rate " + i);
    }

}
