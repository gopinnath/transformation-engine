package com.gopinnath.transformer.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

  @Value("${kafka.producer.employee.topic}")
  private String employeeTopic;
  
  @Value("${kafka.producer.department.topic}")
  private String departmentTopic;

  public enum DataSet {
		EMPLOYEE,DEPARTMENT
  }

  @Autowired
  private KafkaTemplate<String, String> template;

  public void send(String message,DataSet dataSet) {
    template.send(determineTopic(dataSet), message);
  }
  
  private String determineTopic(DataSet dataSet)	{
	switch(dataSet)	{
	case EMPLOYEE : return employeeTopic;
	case DEPARTMENT: return departmentTopic;
	}
	throw new RuntimeException("Unable to determine destination topic.");
  }
}
