package com.gopinnath.transformer.example.domain;

import java.util.Optional;

import org.immutables.value.Value;

@Value.Immutable
public interface DepartmentCommunication extends Communication{

	Optional<Department> pre();
	
	Optional<Department> post();
}
