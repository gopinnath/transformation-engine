package com.gopinnath.transformer.example.web.controller;

import java.io.StringWriter;
import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import com.gopinnath.transformer.example.employee.BodyType;
import com.gopinnath.transformer.example.employee.Communication;
import com.gopinnath.transformer.example.employee.DepartmentType;
import com.gopinnath.transformer.example.employee.EmployeeType;
import com.gopinnath.transformer.example.employee.ExternalReferenceType;
import com.gopinnath.transformer.example.employee.HeaderType;
import com.gopinnath.transformer.example.employee.ObjectFactory;
import com.gopinnath.transformer.example.employee.ReferenceType;
import com.gopinnath.transformer.example.employee.Snapshot;

@Component
public class DataBuilder {
	
	public String buildCommunicationStringFromEmployee(
			Employee employee, String country,String action)	{
		ObjectFactory factory = new ObjectFactory();
		Communication communciation = buildCommunicationWithHeader(factory, country, action);
		
		Snapshot pre = factory.createSnapshot();
		pre.setEmployee(buildEmployee(factory,employee));
		Snapshot post = factory.createSnapshot();
		post.setEmployee(buildEmployee(factory,employee));
		
		BodyType body = factory.createBodyType();
		body.setPre(pre);
		body.setPost(post);
		
		communciation.setBody(body);
		return marshall(factory,communciation);
	}
	
	private EmployeeType buildEmployee(ObjectFactory factory, Employee employee) {
		EmployeeType employeeObj = factory.createEmployeeType();
		employeeObj.setId(employee.getId());
		employeeObj.setFirstName(employee.getFirstName());
		employeeObj.setLastName(employee.getLastName());
		employeeObj.setSalary(employee.getSalary());
		employeeObj.setDoJ(LocalDate.now().minusMonths(12));
		ExternalReferenceType refType = factory.createExternalReferenceType();
		refType.setType(ReferenceType.UUID);
		refType.setValue(UUID.randomUUID().toString());
		employeeObj.getExternalReferences().add(refType);
		return employeeObj;
	}

	public String buildCommunicationStringFromDepartment(
			Department department, String country,String action)	{
		ObjectFactory factory = new ObjectFactory();
		Communication communciation = buildCommunicationWithHeader(factory, country, action);
		
		Snapshot pre = factory.createSnapshot();
		pre.setDepartment(buildDepartment(factory,department));
		Snapshot post = factory.createSnapshot();
		post.setDepartment(buildDepartment(factory,department));
		
		BodyType body = factory.createBodyType();
		body.setPre(pre);
		body.setPost(post);
		
		communciation.setBody(body);
		
		return marshall(factory,communciation);
	}
	
	private HeaderType buildHeader(ObjectFactory factory,String country,String action)	{
		HeaderType header = factory.createHeaderType();
		header.setCorrelationId(UUID.randomUUID().toString());
		header.setTransaction(action);
		header.setCountry(country);
		header.setUser(country+"HR");
		return header;
	}
	
	private Communication buildCommunicationWithHeader(
			ObjectFactory factory,String country,String action)	{
		Communication communciation = factory.createCommunication();
		communciation.setHeader(buildHeader(factory, country, action));
		return communciation;
	}
	
	private DepartmentType buildDepartment(ObjectFactory factory,Department department)	{
		DepartmentType departmentObj = factory.createDepartmentType();
		departmentObj.setId(department.getId());
		departmentObj.setName(department.getName());
		departmentObj.setManager(buildEmployee(factory,department.getManager()));
		departmentObj.getMember().addAll(
				department.getMembers()
				.stream()
				.map(member -> buildEmployee(factory,member))
				.collect(Collectors.toList()));
		return departmentObj;
	}
	
	public String marshall(ObjectFactory factory,Communication communication) {
		try {
			JAXBContext context = JAXBContext.newInstance(Communication.class);
			StringWriter writer = new StringWriter();
			context.createMarshaller().marshal(communication, writer);
			return writer.toString();
		} catch (JAXBException exception) {
			throw new RuntimeException("Unable to build XML String.", exception);
		}
	}
}
