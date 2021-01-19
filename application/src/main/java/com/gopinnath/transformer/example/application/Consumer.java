package com.gopinnath.transformer.example.application;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.gopinnath.transformer.example.config.ApplicationConfiguration;
import com.gopinnath.transformer.example.domain.mapper.DomainMapper;
import com.gopinnath.transformer.example.employee.Communication;
import com.gopinnath.transformer.example.target.TargetMapper;

import io.vavr.concurrent.Future;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class Consumer {

  private final Producer producer;
  
  private final ApplicationConfiguration config;

  @KafkaListener(topics = {"${kafka.consumer.employee.topic}","${kafka.consumer.department.topic}"})
  public void onMessage(ConsumerRecord<String, String> record) {
	  DataSet dataSet = config.determineDataSet(record.topic());
	  DomainMapper domainMapper = config.determineDomainMapper(dataSet);
	  TargetMapper targetMapper = config.determineTargetMapper(dataSet);
	  Communication communication = unmarshalMessage(record.value());
	  Country country = Country.valueOf(communication.getHeader().getCountry());
	  
	  Future.of(()->communication)
			  .map(domainMapper::toDomainObect)
			  .map(targetMapper::transform)
			  .map(targetMapper::serializeAvroJSON)
			  .andThen(finalString -> producer.send(
					  finalString.getOrElseThrow(
							  ()-> new RuntimeException("Transformation did not produce result")),
					  country, dataSet));

  }
  
  private Communication unmarshalMessage(String message)	{
	  try {
		JAXBContext context = JAXBContext.newInstance(Communication.class);
		return (Communication) context.createUnmarshaller().unmarshal(new StringReader(message));
	} catch (JAXBException exception) {
		log.warn("Could Not Parse Message",exception);
		throw new RuntimeException("Could Not Parse Message");
	}
  }
}

