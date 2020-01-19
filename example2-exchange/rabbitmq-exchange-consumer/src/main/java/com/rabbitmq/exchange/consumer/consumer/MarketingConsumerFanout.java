package com.rabbitmq.exchange.consumer.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.exchange.consumer.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class MarketingConsumerFanout {

    private ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "q.hr.marketing")
    public void listen(String message) {
        Employee emp = null;

        try {
            emp = objectMapper.readValue(message, Employee.class);
        } catch (IOException e) {
            log.error("Exception", e);
        }

        log.info("On marketing, employee is {}", emp);
    }

}