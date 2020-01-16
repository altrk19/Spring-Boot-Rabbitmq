package com.course.rabitmqproducer;

import com.course.rabitmqproducer.entity.Employee;
import com.course.rabitmqproducer.producer.EmployeeJsonProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
@Slf4j
public class EmployeeJsonProducerMain implements CommandLineRunner {

    @Autowired
    private EmployeeJsonProducer employeeJsonProducer;


    public static void main(String[] args) {
        SpringApplication.run(EmployeeJsonProducerMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 5; i++) {
            Employee e = new Employee("emp " + i, "Employee " + i, LocalDate.now());
            employeeJsonProducer.sendMessage(e);
        }
    }
}
