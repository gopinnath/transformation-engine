package com.gopinnath.transformer.example.domain;

import java.util.Optional;

import org.immutables.value.Value;

@Value.Immutable
public abstract class EmployeeCommunication implements Communication {
	
	public abstract Optional<Employee> pre();
	
	public abstract Optional<Employee> post();

}
