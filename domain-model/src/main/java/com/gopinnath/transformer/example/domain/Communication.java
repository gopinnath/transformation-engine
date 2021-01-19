package com.gopinnath.transformer.example.domain;

import org.immutables.value.Value;

@Value.Immutable
public interface Communication {
	
	String action();
	
	String user();
	
	String tracingId();
}
