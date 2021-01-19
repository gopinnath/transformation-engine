package com.gopinnath.transformer.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Consumer {
	  
  @KafkaListener(topics = {"${kafka.consumer.usa.employee.topic}",
		  "${kafka.consumer.usa.department.topic}",
		  "${kafka.consumer.india.employee.topic}",
		  "${kafka.consumer.india.department.topic}"})
  public void onMessage(ConsumerRecord<String, String> record) {
	  log.info("Message on Topic " + record.topic() + " is :" + record.value());
  }
  
}

