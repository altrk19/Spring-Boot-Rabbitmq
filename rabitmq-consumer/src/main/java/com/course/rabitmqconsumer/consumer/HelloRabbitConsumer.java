package com.course.rabitmqconsumer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class HelloRabbitConsumer {

    private Logger logger= LoggerFactory.getLogger(HelloRabbitConsumer.class);

    @RabbitListener(queues = "course.hello")
    public void listen(String message) {
        logger.debug("Recieved {}", message);
    }
}
