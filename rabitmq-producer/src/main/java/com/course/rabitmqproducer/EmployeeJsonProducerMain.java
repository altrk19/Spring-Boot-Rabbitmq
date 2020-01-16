package com.course.rabitmqproducer;

import com.course.rabitmqproducer.producer.HelloRabbitProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class HelloRabbitProducerMain implements CommandLineRunner {

	//CommandLineRunner for HelloRabbitProducer, applcation ayaga kalktıgında queue'ya 1 tane mesaj yollanır.

	@Autowired
	private HelloRabbitProducer helloRabbitProducer;

	private Logger logger = LoggerFactory.getLogger(HelloRabbitProducerMain.class);

	public static void main(String[] args) {
		SpringApplication.run(HelloRabbitProducerMain.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		helloRabbitProducer.sendHello("Ali" + Math.random());
		logger.debug("Sended message:Ali");
	}
}
