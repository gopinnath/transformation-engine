package com.gopinnath.transformer.example.domain;

import java.util.Set;

import org.immutables.value.Value;

@Value.Immutable
public interface Department {

	Integer id();
	
	String name();
	
	Employee manager();
	
	Set<Employee> members();
	
}
