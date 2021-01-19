package com.gopinnath.transformer.example.domain.mapper;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gopinnath.transformer.example.domain.Communication;
import com.gopinnath.transformer.example.domain.Department;
import com.gopinnath.transformer.example.domain.ImmutableDepartment;
import com.gopinnath.transformer.example.domain.ImmutableDepartmentCommunication;
import com.gopinnath.transformer.example.employee.Snapshot;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DepartmentDomainMapper implements DomainMapper {
	
	private final EmployeeDomainMapper employeeDomainMapper;

	@Override
	public Communication toDomainObect(com.gopinnath.transformer.example.employee.Communication communication) {
		return ImmutableDepartmentCommunication
				.builder()
				.action(communication.getHeader().getTransaction())
				.user(communication.getHeader().getUser())
				.tracingId(communication.getHeader().getCorrelationId())
				.pre(buildDomainDepartment(communication.getBody().getPre()))
				.post(buildDomainDepartment(communication.getBody().getPost()))
				.build();
	}

	private Optional<Department> buildDomainDepartment(Snapshot snap)	{
		if(snap != null && snap.getEmployee() != null)	{
			return Optional.of(ImmutableDepartment
					.builder()
					.id(snap.getDepartment().getId())
					.name(snap.getDepartment().getName())
					.manager(employeeDomainMapper
							.buildDomainEmployeeFromXmlEmployee(snap.getDepartment().getManager()))
					.members(snap
							.getDepartment()
							.getMember()
							.stream()
							.map(employeeDomainMapper::buildDomainEmployeeFromXmlEmployee)
							.collect(Collectors.toSet()))
					.build());
		}
		return Optional.empty();
	}
}
