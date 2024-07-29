package com.example.stock.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.domain.dto.orderEvent;

@Service
public class kafkaConsumer {
	
	private static final Logger LOG = LoggerFactory.getLogger(kafkaConsumer.class);
	

	@KafkaListener(topics = "${spring.kafka.topic.name}" , groupId= "${spring.kafka.consumer.group-id}" )
	public void consume(orderEvent orderEvent) {
		
		LOG.info(String.format("message received in stockService -> %s", orderEvent));
	}
	
	
}
