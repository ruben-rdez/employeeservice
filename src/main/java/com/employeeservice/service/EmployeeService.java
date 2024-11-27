package com.employeeservice.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.employeeservice.entity.Employee;
import com.employeeservice.exception.UserNotFoundException;
import com.employeeservice.model.EmployeeDto;
import com.employeeservice.repository.EmployeeRepository;
import com.employeeservice.util.EmployeeMapper;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeDto> findAllEmployees(){
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                        .map(employee -> EmployeeMapper.employeeToEmployeeDto(employee))
                        .collect(Collectors.toList());
    }

    public EmployeeDto addEmployee(EmployeeDto employeeDto){
        employeeDto.setEmployeeCode(UUID.randomUUID().toString());
        return EmployeeMapper.employeeToEmployeeDto(
            employeeRepository.save(EmployeeMapper.employeeDtoToEmployee(employeeDto))) ;
    }

    public EmployeeDto findEmployeeById(Long id){
        Employee employee = employeeRepository.findEmployeeById(id)
            .orElseThrow(() -> new UserNotFoundException("Employee by id " + id + " was not found"));
        return EmployeeMapper.employeeToEmployeeDto(employee);    
    }

    public EmployeeDto updateEmployee(EmployeeDto employeeDto){
        return EmployeeMapper.employeeToEmployeeDto(
            employeeRepository.save(EmployeeMapper.employeeDtoToEmployee(employeeDto))) ;
    }

    public void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }
}
