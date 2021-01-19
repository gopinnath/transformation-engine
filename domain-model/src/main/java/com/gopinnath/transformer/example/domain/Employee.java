package com.gopinnath.transformer.example.domain;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import org.immutables.value.Value;

@Value.Immutable
public interface Employee {	
	
	Integer id();
	
	String firstName();
	
	String lastName();

	UUID externalRefernce();
	
	Double salary();
	
	LocalDate dateOfJoining();
	
	Set<String> departments();
}
