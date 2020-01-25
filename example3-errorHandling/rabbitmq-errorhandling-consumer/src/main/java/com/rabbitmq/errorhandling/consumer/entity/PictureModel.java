package com.rabbitmq.errorhandling.consumer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PictureModel {
    private String name;
    private String type;
    private String source;
    private long size;
}
