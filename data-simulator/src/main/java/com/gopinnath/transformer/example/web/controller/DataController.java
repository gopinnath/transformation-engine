package com.gopinnath.transformer.example.web.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gopinnath.transformer.example.Producer;
import com.gopinnath.transformer.example.Producer.DataSet;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DataController {

	private final Producer producer;
	
	private final DataBuilder builder;

	@PostMapping("/country/{country}/employees/{action}")
	public void postEmployee(@RequestBody Employee employee,
			@PathVariable String country,@PathVariable String action) {
		producer.send(builder.buildCommunicationStringFromEmployee(employee,country,action),
				DataSet.EMPLOYEE);
	}

	@PostMapping("/country/{country}/departments/{action}")
	public void postDepartment(@RequestBody Department department,
			@PathVariable String country,@PathVariable String action) {
		producer.send(builder.buildCommunicationStringFromDepartment(department,country,action),
				DataSet.DEPARTMENT);
	}

}
