package com.rabbitmq.retry.direct.exchange.producer;

import com.rabbitmq.retry.direct.exchange.producer.entity.PictureModel;
import com.rabbitmq.retry.direct.exchange.producer.producer.RetryPictureProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@Slf4j
public class ProducerApplication implements CommandLineRunner {

	@Autowired
	private RetryPictureProducer retryPictureProducer;

	private static final List<String> SOURCES = Arrays.asList("mobile", "web");
	private static final List<String> TYPES = Arrays.asList("jpg", "png", "svg");

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 10; i++) {
			PictureModel p = new PictureModel();

			p.setName("Picture " + i);
			p.setSize(ThreadLocalRandom.current().nextLong(9001, 10001));
			p.setSource(SOURCES.get(i % SOURCES.size()));
			p.setType(TYPES.get(i % TYPES.size()));

			retryPictureProducer.sendMessage(p);
			log.debug("sended message direct exchange, picture:{}", p);
		}
	}

}
