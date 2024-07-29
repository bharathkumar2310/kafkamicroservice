package com.example.email.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.kafka.listener.ContainerProperties;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;

import java.util.HashMap;
import java.util.Map;
// this is another method of configuring kafka consumer another way is done for stock consumer
@Configuration
public class KafkaConsumerConfig {
	
	@Autowired
	private KafkaMessageListener KafkaMessageListener;

    @Bean
    public ConcurrentMessageListenerContainer<String, String> kafkaListenerContainer(KafkaMessageListener kafkaMessageListener) {
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "myGroup");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        consumerProps.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        consumerProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "60000");

        ConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);

        ContainerProperties containerProps = new ContainerProperties("order_topic");
        containerProps.setPollTimeout(100);
        containerProps.setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        // Create ConcurrentMessageListenerContainer with the ConsumerFactory and ContainerProperties
        ConcurrentMessageListenerContainer<String, String> container =
                new ConcurrentMessageListenerContainer<>(consumerFactory, containerProps);

        // Set the message listener using a lambda expression
//        container.setupMessageListener(KafkaMessageListener);

        // Set concurrency to handle multiple partitions
        container.setConcurrency(3);

        return container;
    }
}
