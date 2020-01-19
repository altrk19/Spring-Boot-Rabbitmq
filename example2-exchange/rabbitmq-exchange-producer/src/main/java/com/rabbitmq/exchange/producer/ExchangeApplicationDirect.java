package com.rabbitmq.exchange.producer;

import com.rabbitmq.exchange.producer.entity.PictureModel;
import com.rabbitmq.exchange.producer.producer.PictureProducerDirect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

//@SpringBootApplication
@Slf4j
public class ExchangeApplicationDirect implements CommandLineRunner {

    @Autowired
    private PictureProducerDirect pictureProducer;

    private static final List<String> SOURCES = Arrays.asList("mobile", "web");
    private static final List<String> TYPES = Arrays.asList("jpg", "png", "svg");

    public static void main(String[] args) {
        SpringApplication.run(ExchangeApplicationDirect.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 10; i++) {
            PictureModel p = new PictureModel();

            p.setName("Picture " + i);
            p.setSize(ThreadLocalRandom.current().nextLong(1, 10001));
            p.setSource(SOURCES.get(i % SOURCES.size()));
            p.setType(TYPES.get(i % TYPES.size()));

            pictureProducer.sendMessage(p);
            log.debug("sended message direct exchange, picture:{}", p);
        }
    }
}

//q.picture.image  queue olustur
//q.picture.vector  queue olustur
//x.picture  exchane olustur
//q.picture.image  queue'sunu jpg routing key'i ile x.picture exchange'ine ekle
//q.picture.image  queue'sunu png routing key'i ile x.picture exchange'ine ekle
//q.picture.vector  queue'sunu svg routing key'i ile x.picture exchange'ine ekle