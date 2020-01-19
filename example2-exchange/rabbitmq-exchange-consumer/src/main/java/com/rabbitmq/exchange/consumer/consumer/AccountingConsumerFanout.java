package com.rabbitmq.exchange.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.exchange.consumer.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountingConsumerFanout {
    private ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "q.hr.accounting")
    public void listen(String message) {
        Employee employee = null;

        try {
            employee = objectMapper.readValue(message, Employee.class);
        } catch (JsonProcessingException e) {
            log.error("Exception occurred", e);
        }
        log.debug("On accounting, employee is {}", employee);
    }
}
