package com.rabbitmq.exchange.producer;

import com.rabbitmq.exchange.producer.entity.PictureModel;
import com.rabbitmq.exchange.producer.producer.PictureProducerDirect;
import com.rabbitmq.exchange.producer.producer.PictureProducerTopic;
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
public class ExchangeApplicationTopic implements CommandLineRunner {

    @Autowired
    private PictureProducerTopic pictureProducerTopic;

    private static final List<String> SOURCES = Arrays.asList("mobile", "web");
    private static final List<String> TYPES = Arrays.asList("jpg", "png", "svg");

    public static void main(String[] args) {
        SpringApplication.run(ExchangeApplicationTopic.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 10; i++) {
            PictureModel p = new PictureModel();

            p.setName("Picture " + i);
            p.setSize(ThreadLocalRandom.current().nextLong(1, 10001));
            p.setSource(SOURCES.get(i % SOURCES.size()));
            p.setType(TYPES.get(i % TYPES.size()));

            pictureProducerTopic.sendMessage(p);
            log.debug("sended message topic exchange, picture:{}", p);
        }
    }
}

//q.picture.image  queue olustur
//q.picture.vector  queue olustur
//q.picture.filter queue olustur
//q.picture.log queue olustur
//x.picture2  exchange olustur

//q.picture.image  queue'sunu *.*.png routing key'i ile x.picture2 exchange'ine ekle
//q.picture.image  queue'sunu #.jpg routing key'i ile x.picture2 exchange'ine ekle
//q.picture.filter  queue'sunu mobile.#  routing key'i ile x.picture2 exchange'ine ekle
//q.picture.vector  queue'sunu *.*.svg routing key'i ile x.picture2 exchange'ine ekle
//q.picture.log  queue'sunu *.large.svg routing key'i ile x.picture2 exchange'ine ekle