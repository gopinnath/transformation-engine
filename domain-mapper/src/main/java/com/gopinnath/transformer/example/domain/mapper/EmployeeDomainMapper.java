package com.gopinnath.transformer.example.domain.mapper;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gopinnath.transformer.example.domain.Communication;
import com.gopinnath.transformer.example.domain.Employee;
import com.gopinnath.transformer.example.domain.ImmutableEmployee;
import com.gopinnath.transformer.example.domain.ImmutableEmployeeCommunication;
import com.gopinnath.transformer.example.employee.EmployeeType;
import com.gopinnath.transformer.example.employee.ReferenceType;
import com.gopinnath.transformer.example.employee.Snapshot;

@Service
public class EmployeeDomainMapper implements DomainMapper{

	@Override
	public Communication toDomainObect(com.gopinnath.transformer.example.employee.Communication communication) {
		return ImmutableEmployeeCommunication
				.builder()
				.action(communication.getHeader().getTransaction())
				.user(communication.getHeader().getUser())
				.tracingId(communication.getHeader().getCorrelationId())
				.pre(buildDomainEmployee(communication.getBody().getPre()))
				.post(buildDomainEmployee(communication.getBody().getPost()))
				.build();
	}

	private Optional<Employee> buildDomainEmployee(Snapshot snap) {
		if(snap != null && snap.getEmployee() != null)	{
			return Optional.of(buildDomainEmployeeFromXmlEmployee(snap.getEmployee()));
		}
		return Optional.empty();
	}

	public Employee buildDomainEmployeeFromXmlEmployee(EmployeeType xmlEmployee)	{
		return ImmutableEmployee
				.builder()
				.id(xmlEmployee.getId())
				.firstName(xmlEmployee.getFirstName())
				.lastName(xmlEmployee.getLastName())
				.salary(xmlEmployee.getSalary())
				.dateOfJoining(xmlEmployee.getDoJ())
				.addAllDepartments(xmlEmployee
						.getDepartment()
						.stream()
						.map(dep -> dep.getName())
						.collect(Collectors.toSet()))
				.externalRefernce(
						UUID.fromString(xmlEmployee
							.getExternalReferences()
							.stream()
							.filter(ref -> ref.getType().equals(ReferenceType.UUID))
							.findFirst().get().getValue()))
				.build();
	}
}
