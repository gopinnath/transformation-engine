package com.gopinnath.transformer.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gopinnath.transformer.example.application.DataSet;
import com.gopinnath.transformer.example.domain.mapper.DepartmentDomainMapper;
import com.gopinnath.transformer.example.domain.mapper.DomainMapper;
import com.gopinnath.transformer.example.domain.mapper.EmployeeDomainMapper;
import com.gopinnath.transformer.example.target.DepartmentTargetMapper;
import com.gopinnath.transformer.example.target.EmployeeTargetMapper;
import com.gopinnath.transformer.example.target.TargetMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationConfiguration {

	private final EmployeeDomainMapper employeeDomainMapper;
	
	private final DepartmentDomainMapper departmentDomainMapper;
	
	private final EmployeeTargetMapper employeeTargetMapper;
	
	private final DepartmentTargetMapper departmentTargetMapper;
	
	@Value("${kafka.consumer.employee.topic}")
	private String employeeTopic;
	
	@Value("${kafka.consumer.department.topic}")
	private String departmentTopic;
	
	public DomainMapper determineDomainMapper(DataSet dataSet)	{
		if(DataSet.EMPLOYEE.equals(dataSet))	{
			return employeeDomainMapper;
		} else if(DataSet.DEPARTMENT.equals(dataSet))	{
			return departmentDomainMapper;
		}
		throw new RuntimeException("Unable to determine Domain Mapper.");
	}
	
	public TargetMapper determineTargetMapper(DataSet dataSet)	{
		if(DataSet.EMPLOYEE.equals(dataSet))	{
			return employeeTargetMapper;
		} else if(DataSet.DEPARTMENT.equals(dataSet))	{
			return departmentTargetMapper;
		}
		throw new RuntimeException("Unable to determine Target Mapper.");
	}
	
	public DataSet determineDataSet(String topic)	{
		if(topic !=null && topic.equals(employeeTopic))	{
			return DataSet.EMPLOYEE;
		} else if(topic !=null && topic.equals(departmentTopic))	{
			return DataSet.DEPARTMENT;
		}
		throw new RuntimeException("Unable to determine Data Set.");
	}
}
