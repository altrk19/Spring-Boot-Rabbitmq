package com.rabbitmq.exchange.producer;

import com.rabbitmq.exchange.producer.entity.Employee;
import com.rabbitmq.exchange.producer.producer.HumanResourceProducerFanout;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

//@SpringBootApplication
@Slf4j
public class ExchangeApplicationFanout implements CommandLineRunner {

	@Autowired
	private HumanResourceProducerFanout humanResourceProducerFanout;

	public static void main(String[] args) {
		SpringApplication.run(ExchangeApplicationFanout.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 5; i++) {
			Employee e = new Employee("emp " + i, "Employee " + i, LocalDate.now());
			humanResourceProducerFanout.sendMessage(e);
			log.debug("sended message employe:{}",e);
		}
	}
}

//q.hr.accounting  queue olustur
//q.hr.marketing  queue olustur
//x.hr  exchane olustur
//bu exchange'e yukarıdaki 2 queue'yu bind et.