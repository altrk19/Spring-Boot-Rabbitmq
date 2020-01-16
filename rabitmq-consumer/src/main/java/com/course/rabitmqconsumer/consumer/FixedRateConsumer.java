package com.course.rabitmqconsumer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class FixedRateConsumer {

    private static final Logger logger = LoggerFactory.getLogger(FixedRateConsumer.class);

    @RabbitListener(queues = "course.fixedrate", concurrency = "3")  //3 tane consumer threadi setlenir
    public void listen(String message) {
        logger.debug("Received message: {} in thread:{}", message, Thread.currentThread().getName());

        try {
            //çalışmakta olan threadi 2saniye bekletir
            Thread.sleep(ThreadLocalRandom.current().nextLong(2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
