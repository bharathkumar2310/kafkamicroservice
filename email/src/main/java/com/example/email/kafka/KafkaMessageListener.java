package com.example.email.kafka;

	
	import org.apache.kafka.clients.consumer.ConsumerRecord;
	import org.springframework.kafka.listener.AcknowledgingMessageListener;
	import org.springframework.kafka.support.Acknowledgment;
	import org.springframework.stereotype.Service;
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;

	@Service
	public class KafkaMessageListener implements AcknowledgingMessageListener<String, String> {

	    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageListener.class);

	    @Override
	    public void onMessage(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
	        LOG.info("Received message: key={}, value={}, partition={}, offset={}", 
	                 record.key(), record.value(), record.partition(), record.offset());

	        // Process the message
	        processMessage(record.value());

	        // Acknowledge the message
	        acknowledgment.acknowledge();
	    }

	    private void processMessage(String message) {
	        // Your message processing logic here
	        LOG.info("Processing message: {}", message);
	    }
	}


