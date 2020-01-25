package com.rabbitmq.errorhandling.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

}
//Consumer tarafında mesajı reject etmek için 2 yöntem var
//1-manuel reject et
//2-exception fırlat