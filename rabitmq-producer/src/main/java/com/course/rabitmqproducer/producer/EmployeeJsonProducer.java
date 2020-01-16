package com.course.rabitmqproducer.producer;

import com.course.rabitmqproducer.entity.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeJsonProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(Employee employee) {
        try {
            String employeeStr = objectMapper.writeValueAsString(employee);
            rabbitTemplate.convertAndSend("course.employee", employeeStr);
            log.debug("Sended employee json:{}", employeeStr);
        } catch (JsonProcessingException e) {
            log.error("Exception occurred", e);
        }
    }
}
