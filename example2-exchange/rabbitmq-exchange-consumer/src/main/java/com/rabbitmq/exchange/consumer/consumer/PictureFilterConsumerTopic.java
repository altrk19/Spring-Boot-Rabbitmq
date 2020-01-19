package com.rabbitmq.exchange.consumer.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.exchange.consumer.entity.PictureModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PictureFilterConsumerTopic {

	private ObjectMapper objectMapper = new ObjectMapper();
	
	private static final Logger log = LoggerFactory.getLogger(PictureFilterConsumerTopic.class);
	
	@RabbitListener(queues = "q.picture.filter")
	public void listen(String message) throws IOException {
		PictureModel p = objectMapper.readValue(message, PictureModel.class);
		log.debug("On picture.filter : {}", p);
	}
	
}
