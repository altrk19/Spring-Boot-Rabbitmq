package com.rabbitmq.exchange.producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.exchange.producer.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HumanResourceProducerFanout {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public void sendMessage(Employee emp) {
		try {
			String empStr = objectMapper.writeValueAsString(emp);
			rabbitTemplate.convertAndSend("x.hr", "", empStr);
		} catch (JsonProcessingException e) {
			log.error("Exception occurred", e);
		}
	}
	
}
