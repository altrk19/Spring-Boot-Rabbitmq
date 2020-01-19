package com.rabbitmq.exchange.consumer.entity;

import com.course.rabitmqconsumer.json.CustomLocalDateDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @JsonProperty("employee_id")
    private String employeeId;

    private String name;

    @JsonProperty("birth_date")
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate birthDate;
}
