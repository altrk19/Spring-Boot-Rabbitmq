package com.rabbitmq.exchange.producer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rabbitmq.exchange.producer.json.CustomLocalDateSerializer;
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
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate birthDate;
}
