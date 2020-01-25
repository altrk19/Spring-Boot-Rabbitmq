package com.rabbitmq.retry.direct.exchange.consumer.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RabbitmqHeaderXDeath {
	private int count;
	private String exchange;
	private String queue;
	private String reason;
	private List<String> routingKeys;
	private Date time;
}
