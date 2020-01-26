package com.rabbitmq.spring.retry.mechanism.producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.spring.retry.mechanism.producer.entity.PictureModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpringPictureProducer {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	private ObjectMapper objectMapper  = new ObjectMapper();
	
	public void sendMessage(PictureModel p) throws JsonProcessingException {
		String json = objectMapper.writeValueAsString(p);
		rabbitTemplate.convertAndSend("x.spring.work", p.getType(), json);
	}
}

//add following exchanges
//x.spring.work   x.spring.dead

//add following queues
//q.spring.image.work   q.spring.vector.work  and  dlx exchange these queues -> dlx: x.spring.dead

//add another queues
//q.spring.image.dead     q.spring.vector.dead

//add bindings to x.spring.work exchange:
//q.spring.image.work -> jpg
//q.spring.image.work -> png
//q.spring.vector.work -> svg

//add bindings to x.spring.dead exchange:
//q.spring.image.dead -> jpg
//q.spring.image.dead -> png
//q.spring.vector.dead -> svg

//NOTE: there is no exchange and queue for wait