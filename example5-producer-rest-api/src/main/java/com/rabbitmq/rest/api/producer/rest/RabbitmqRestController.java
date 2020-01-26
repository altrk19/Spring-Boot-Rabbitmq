package com.rabbitmq.rest.api.producer.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
public class RabbitmqRestController {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@PostMapping(path = { "/api/publish/{exchange}/{routingKey}",
			"/api/publish/{exchange}" }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> publish(@PathVariable(name = "exchange", required = true) String exchange,
			@PathVariable(name = "routingKey", required = false) String routingKey,
			@RequestBody String message) {
		if (!isValidJson(message)) {
			return ResponseEntity.badRequest().body(Boolean.FALSE.toString());
		}
		
		try {
			rabbitTemplate.convertAndSend(exchange, routingKey, message);
			return ResponseEntity.ok().body(Boolean.TRUE.toString());
		} catch (Exception e) {
			log.error("Error when publishing : {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	/**
	 *
	 * @param string string
	 * @return boolean
	 */
	private static boolean isValidJson(String string) {
		try {
			OBJECT_MAPPER.readTree(string);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}

//add following exchanges
//x.fanout  x.direct
//add folllowing queues
//q.fanout.one    q.fanout.one   q.direct.one    q.direct.two
//add following bindins into direct exchange
//q.direct.one -> one       q.direct.two -> two (routing key)

//send following request from postman
//http://localhost:8080/api/publish/x.fanout
//{
//    "text": "hello"
//}