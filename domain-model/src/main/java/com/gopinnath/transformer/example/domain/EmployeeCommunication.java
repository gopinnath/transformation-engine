package com.gopinnath.transformer.example.domain;

import java.util.Optional;

import org.immutables.value.Value;

@Value.Immutable
public interface EmployeeCommunication extends Communication {
	
	Optional<Employee> pre();
	
	Optional<Employee> post();

}
