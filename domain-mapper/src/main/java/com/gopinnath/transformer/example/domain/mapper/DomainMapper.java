package com.gopinnath.transformer.example.domain.mapper;

import com.gopinnath.transformer.example.domain.Communication;

public interface DomainMapper {
	
	Communication toDomainObect(com.gopinnath.transformer.example.employee.Communication communication);
	
}
