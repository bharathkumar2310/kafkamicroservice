package com.example.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.dto.Order;
import com.example.domain.dto.orderEvent;
import  com.example.order.kafka.kafkaProducer;


@RestController
public class orderController {
	

	@Autowired
	private kafkaProducer kafkaProducer;
	
	@PostMapping("/orders")
	public String placeOrder(@RequestBody Order order) {
		 orderEvent orderEvent = new orderEvent();

		orderEvent.setStatus("Pending");
		orderEvent.setMessage("order is in pending state");
		orderEvent.setOrder(order);
		kafkaProducer.sendMessage(orderEvent);
		return "Order Placed Successfully";
		
		
	}
}
