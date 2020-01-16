package com.course.rabitmqproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//@SpringBootApplication
//@EnableScheduling
public class FixedRateProducerMain {
	//EnableScheduling kullandıgımız için bu sınıfta producer tanımlamaya gerek yok
	public static void main(String[] args) {
		SpringApplication.run(FixedRateProducerMain.class, args);
	}
}
