package com.gopinnath.transformer.example.web.controller;

import java.util.List;

import lombok.Data;

@Data
public class Department {

	private Integer id;

	private String name;
	
	private Employee manager;
	
	private List<Employee> members;
	
}
