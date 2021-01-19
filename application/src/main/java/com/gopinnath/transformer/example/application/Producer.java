package com.gopinnath.transformer.example.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

  @Value("${kafka.producer.usa.employee.topic}")
  private String usaEmployeeTopic;
  
  @Value("${kafka.producer.usa.department.topic}")
  private String usaDepartmentTopic;
  
  @Value("${kafka.producer.india.employee.topic}")
  private String indiaEmployeeTopic;
  
  @Value("${kafka.producer.india.department.topic}")
  private String indiaDepartmentTopic;

  @Autowired
  private KafkaTemplate<String, String> template;

  public void send(String message, Country country, DataSet dataSet) {
    template.send(determineTopicName(country,dataSet), message);
  }
  
  private String determineTopicName(Country country, DataSet dataSet)	{
	  switch(country) {
	  	case USA : 
	  		if(dataSet.equals(DataSet.DEPARTMENT)) {
	  			return usaDepartmentTopic;
	  		}
	  		if(dataSet.equals(DataSet.EMPLOYEE))	{
	  			return usaEmployeeTopic;
	  		}
	  		break;
	  	case INDIA: 
	  		if(dataSet.equals(DataSet.DEPARTMENT)) {
	  			return indiaDepartmentTopic;
	  		}
	  		if(dataSet.equals(DataSet.EMPLOYEE))	{
	  			return indiaEmployeeTopic;
	  		}
	  		break;	  			
	  	default: 
	  		throw new RuntimeException("Unable to Determine the destination topic.");
	  }
	  throw new RuntimeException("Unable to Determine the destination topic.");
  }
}
