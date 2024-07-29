package com.example.order.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.example.domain.dto.orderEvent;

@Service
public class kafkaProducer {
	
	@Value("${spring.kafka.topic.name}")
	private String orderTopic;

	private static final Logger LOG = LoggerFactory.getLogger(kafkaProducer.class);
	
	@Autowired
	KafkaTemplate<String,orderEvent>kafkaTemplate;
	
	public void sendMessage(orderEvent Event) {
		
		LOG.info(String.format("message sent -> %s", Event));
		Message<orderEvent>message = MessageBuilder.withPayload(Event).setHeader(KafkaHeaders.TOPIC, orderTopic).build();
		kafkaTemplate.send(message);

	}
}
