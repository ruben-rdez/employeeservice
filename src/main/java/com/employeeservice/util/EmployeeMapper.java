package com.employeeservice.util;

import com.employeeservice.entity.Employee;
import com.employeeservice.model.EmployeeDto;

public class EmployeeMapper {

    public static EmployeeDto employeeToEmployeeDto(Employee employee){
        EmployeeDto employeeDto = new EmployeeDto(employee.getId(), employee.getName(), 
            employee.getEmail(), employee.getJobTitle(), employee.getPhone(), 
            employee.getImageUrl(), employee.getEmployeeCode());
        return employeeDto;
    }

    public static Employee employeeDtoToEmployee(EmployeeDto employeeDto){
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setJobTitle(employeeDto.getJobTitle());
        employee.setPhone(employeeDto.getPhone());
        employee.setImageUrl(employeeDto.getImageUrl());
        employee.setEmployeeCode(employeeDto.getEmployeeCode());
        return employee;
    }
}
