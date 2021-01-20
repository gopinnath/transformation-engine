package com.gopinnath.transformer.example.domain;

import java.util.Optional;

import org.immutables.value.Value;

@Value.Immutable
public abstract class DepartmentCommunication implements Communication{

	public abstract Optional<Department> pre();
	
	public abstract Optional<Department> post();
}
